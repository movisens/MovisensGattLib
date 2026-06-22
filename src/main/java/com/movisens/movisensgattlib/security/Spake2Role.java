package com.movisens.movisensgattlib.security;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.spec.ECPoint;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * One side of a SPAKE2 handshake (non-augmented, RFC 9382, ciphersuite
 * P256-SHA256-HKDF-HMAC). The same class drives both parties; party {@code A}
 * uses the seed point M, party {@code B} uses N.
 *
 * <p>Follows RFC 9382 exactly so the implementation is anchored by the Appendix B
 * test vectors (see {@code Spake2Rfc9382VectorTest}):</p>
 * <ul>
 *   <li>{@code pA = x*G + w*M}, {@code pB = y*G + w*N}, {@code K = x*(pB - w*N)}.</li>
 *   <li>{@code TT = lv(A)||lv(B)||lv(pA)||lv(pB)||lv(K)||lv(w)} (8-byte little-endian lengths).</li>
 *   <li>{@code Ke||Ka = SHA256(TT)} (16 bytes each).</li>
 *   <li>{@code KcA||KcB = HKDF-SHA256(salt=nil, IKM=Ka, info="ConfirmationKeys", L=32)}.</li>
 *   <li>{@code cA = HMAC-SHA256(KcA, TT)}, {@code cB = HMAC-SHA256(KcB, TT)}.</li>
 * </ul>
 *
 * <p>BouncyCastle-free (uses {@link P256} + JCE HMAC). The point arithmetic is not
 * constant-time; acceptable on the smartphone side.</p>
 */
public final class Spake2Role
{
    /** A uses M, B uses N (RFC 9382 role assignment). */
    public enum Role
    {
        A, B
    }

    private static final byte[] CONFIRMATION_INFO =
        "ConfirmationKeys".getBytes(StandardCharsets.US_ASCII);
    private static final int HALF = 16;

    private final Role role;
    private final byte[] idA;
    private final byte[] idB;
    private final BigInteger w;
    private final ECPoint ownMask;
    private final ECPoint peerMask;
    private final SecureRandom rng;

    private BigInteger ephemeral;
    private byte[] ownShare;
    private byte[] transcript;
    private byte[] sharedPoint;
    private byte[] sessionKey;
    private byte[] ownConfirmKey;
    private byte[] peerConfirmKey;

    /** Constructs a role with the password integer {@code w} given directly (RFC vectors). */
    public Spake2Role(Role role, byte[] idA, byte[] idB, BigInteger w, SecureRandom rng)
    {
        this.role = role;
        this.idA = idA.clone();
        this.idB = idB.clone();
        this.w = w.mod(P256.N);
        this.ownMask = role == Role.A ? P256.M : P256.N_POINT;
        this.peerMask = role == Role.A ? P256.N_POINT : P256.M;
        this.rng = rng;
    }

    /** Convenience for the colour-code pairing: {@code w = SHA-256(code) mod n}. */
    public static Spake2Role forColourCode(Role role, byte[] idA, byte[] idB, byte[] code, SecureRandom rng)
        throws GeneralSecurityException
    {
        return new Spake2Role(role, idA, idB, P256.hashToScalar(code), rng);
    }

    /** Computes this side's share with a fresh random scalar. */
    public byte[] createShare() throws GeneralSecurityException
    {
        return createShareWithScalar(randomScalar());
    }

    /** Computes this side's share with a caller-supplied scalar (test/KAT use only). */
    byte[] createShareWithScalar(BigInteger scalar) throws GeneralSecurityException
    {
        ephemeral = scalar.mod(P256.N);
        ECPoint share = P256.add(P256.scalarMul(ephemeral, P256.G), P256.scalarMul(w, ownMask));
        ownShare = P256.encode(share);
        return ownShare.clone();
    }

    /** Consumes the peer share, derives {@code K} and the session/confirmation keys. */
    public void setPeerShare(byte[] peerShareEncoded) throws GeneralSecurityException
    {
        if (ownShare == null)
        {
            throw new PakeException("INVALID_PAKE_STATE: createShare() must run first");
        }

        ECPoint peerPoint = P256.decode(peerShareEncoded); // validates on-curve / not infinity
        ECPoint unmasked = P256.add(peerPoint, P256.negate(P256.scalarMul(w, peerMask)));
        ECPoint k = P256.scalarMul(ephemeral, unmasked); // cofactor 1 on P-256
        if (k == ECPoint.POINT_INFINITY)
        {
            throw new PakeException("INVALID_POINT: shared point is the identity");
        }

        sharedPoint = P256.encode(k);
        deriveKeys(peerShareEncoded);
    }

    /** This side's confirmation MAC: {@code cA} for role A, {@code cB} for role B. */
    public byte[] ownConfirm()
    {
        requireKeys();
        return hmac(ownConfirmKey, transcript);
    }

    /** Verifies the peer's confirmation MAC in constant time. */
    public boolean verifyPeerConfirm(byte[] peerConfirm)
    {
        requireKeys();
        return MessageDigest.isEqual(hmac(peerConfirmKey, transcript), peerConfirm);
    }

    /** The negotiated 16-byte AES session key {@code Ke}. */
    public byte[] sessionKey()
    {
        requireKeys();
        return sessionKey.clone();
    }

    /** SEC1-uncompressed encoding of the shared point {@code K} (test accessor). */
    byte[] sharedSecretPoint()
    {
        requireKeys();
        return sharedPoint.clone();
    }

    private void deriveKeys(byte[] peerShareEncoded)
    {
        byte[] pA = role == Role.A ? ownShare : peerShareEncoded;
        byte[] pB = role == Role.B ? ownShare : peerShareEncoded;

        ByteArrayOutputStream tt = new ByteArrayOutputStream();
        writeChunk(tt, idA);
        writeChunk(tt, idB);
        writeChunk(tt, pA);
        writeChunk(tt, pB);
        writeChunk(tt, sharedPoint);
        writeChunk(tt, toFixed32(w));
        transcript = tt.toByteArray();

        byte[] hash = sha256(transcript);
        sessionKey = java.util.Arrays.copyOfRange(hash, 0, HALF); // Ke
        byte[] ka = java.util.Arrays.copyOfRange(hash, HALF, 32);

        byte[] confirmKeys = hkdfSha256(new byte[32], ka, CONFIRMATION_INFO, 32);
        byte[] kcA = java.util.Arrays.copyOfRange(confirmKeys, 0, HALF);
        byte[] kcB = java.util.Arrays.copyOfRange(confirmKeys, HALF, 32);

        ownConfirmKey = role == Role.A ? kcA : kcB;
        peerConfirmKey = role == Role.A ? kcB : kcA;
    }

    private static void writeChunk(ByteArrayOutputStream out, byte[] data)
    {
        long length = data.length; // long: avoid int shift-by-32 wraparound
        for (int i = 0; i < 8; i++)
        {
            out.write((int) ((length >>> (8 * i)) & 0xFF)); // 8-byte little-endian length
        }
        out.write(data, 0, data.length);
    }

    private BigInteger randomScalar()
    {
        while (true)
        {
            BigInteger candidate = new BigInteger(P256.N.bitLength(), rng);
            if (candidate.signum() != 0 && candidate.compareTo(P256.N) < 0)
            {
                return candidate;
            }
        }
    }

    private void requireKeys()
    {
        if (sessionKey == null)
        {
            throw new IllegalStateException("keys not derived yet; call setPeerShare() first");
        }
    }

    /** HKDF-SHA256 with output length <= 32 (single expand block). salt=nil -> zero-filled. */
    private static byte[] hkdfSha256(byte[] salt, byte[] ikm, byte[] info, int length)
    {
        byte[] prk = hmac(salt, ikm);
        byte[] input = new byte[info.length + 1];
        System.arraycopy(info, 0, input, 0, info.length);
        input[info.length] = 0x01;
        byte[] block = hmac(prk, input);
        return java.util.Arrays.copyOf(block, length);
    }

    private static byte[] hmac(byte[] key, byte[] data)
    {
        try
        {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(key, "HmacSHA256"));
            return mac.doFinal(data);
        }
        catch (GeneralSecurityException e)
        {
            throw new IllegalStateException("HMAC-SHA256 unavailable", e);
        }
    }

    private static byte[] sha256(byte[] data)
    {
        try
        {
            return MessageDigest.getInstance("SHA-256").digest(data);
        }
        catch (GeneralSecurityException e)
        {
            throw new IllegalStateException("SHA-256 unavailable", e);
        }
    }

    private static byte[] toFixed32(BigInteger value)
    {
        byte[] raw = value.toByteArray();
        byte[] out = new byte[32];
        int offset = raw.length > 1 && raw[0] == 0 ? 1 : 0;
        int length = Math.min(raw.length - offset, 32);
        System.arraycopy(raw, raw.length - length, out, 32 - length, length);
        return out;
    }
}
