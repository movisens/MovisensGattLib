package com.movisens.movisensgattlib.security;

import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Arrays;

import com.movisens.movisensgattlib.attributes.PakeClientConfirm1;
import com.movisens.movisensgattlib.attributes.PakeClientConfirm2;
import com.movisens.movisensgattlib.attributes.PakeClientShare1;
import com.movisens.movisensgattlib.attributes.PakeClientShare2;
import com.movisens.movisensgattlib.attributes.PakeSensorConfirm1;
import com.movisens.movisensgattlib.attributes.PakeSensorConfirm2;
import com.movisens.movisensgattlib.attributes.PakeSensorShare1;
import com.movisens.movisensgattlib.attributes.PakeSensorShare2;

/**
 * Smartphone/client side of the balanced-SPAKE2 colour-code pairing (RFC 9382,
 * ciphersuite P256-SHA256-HKDF-HMAC). The client is party B; the sensor is party A.
 *
 * <p>Production-facing, stateful handshake object. The caller drives a flat sequence
 * of GATT attribute writes/reads — there is no loop and the SPAKE2 details (role,
 * transcript, key schedule, point arithmetic) are hidden:</p>
 *
 * <pre>{@code
 * SpakePairingClient client = new SpakePairingClient(sensorId, clientId, colourCode);
 * conn.write(PAKE_CLIENT_SHARE,   client.clientShare());
 * client.setSensorShare(conn.read(PAKE_SENSOR_SHARE));
 * conn.write(PAKE_CLIENT_CONFIRM, client.clientConfirm());
 * client.verifySensorConfirm(conn.read(PAKE_SENSOR_CONFIRM)); // throws on mismatch
 * byte[] sessionKey = client.sessionKey();                    // MITM-verified
 * }</pre>
 *
 * <p>BouncyCastle-free (see {@link P256}/{@link Spake2Role}). Anchored to the RFC 9382
 * Appendix B test vectors. The colour code is the SPAKE2 password and must be encoded
 * to bytes identically on the firmware side. The {@code sensorId}/{@code clientId}
 * identities are bound into the transcript and must match the sensor.</p>
 */
public final class SpakePairingClient
{
    private final Spake2Role role;

    private byte[] clientShare;
    private boolean sensorShareConsumed;
    private boolean sensorVerified;

    public SpakePairingClient(byte[] sensorId, byte[] clientId, byte[] colourCode)
        throws GeneralSecurityException
    {
        this(sensorId, clientId, colourCode, new SecureRandom());
    }

    SpakePairingClient(byte[] sensorId, byte[] clientId, byte[] colourCode, SecureRandom rng)
        throws GeneralSecurityException
    {
        // Sensor = party A (uses M), client = party B (uses N).
        this.role = Spake2Role.forColourCode(Spake2Role.Role.B, sensorId, clientId, colourCode, rng);
    }

    /**
     * Payload to write to {@code pake_client_share} — a SEC1 <em>compressed</em> point
     * (33 bytes) to keep the attribute small. Idempotent within one session.
     */
    public byte[] clientShare() throws GeneralSecurityException
    {
        if (clientShare == null)
        {
            clientShare = P256.compress(role.createShare());
        }
        return clientShare.clone();
    }

    /**
     * Consumes the {@code pake_sensor_share} read (SEC1 compressed, 33 bytes) and derives
     * the session/confirmation state.
     */
    public void setSensorShare(byte[] sensorShare) throws GeneralSecurityException
    {
        if (clientShare == null)
        {
            throw new PakeException("INVALID_PAKE_STATE: clientShare() must be sent first");
        }
        role.setPeerShare(P256.decompress(sensorShare));
        sensorShareConsumed = true;
    }

    /** Payload to write to {@code pake_client_confirm}. Requires {@link #setSensorShare} first. */
    public byte[] clientConfirm() throws GeneralSecurityException
    {
        if (!sensorShareConsumed)
        {
            throw new PakeException("INVALID_PAKE_STATE: setSensorShare() must run first");
        }
        return role.ownConfirm();
    }

    /**
     * Consumes the {@code pake_sensor_confirm} read and verifies it.
     * @throws PakeException if the sensor confirmation is invalid (wrong code / MITM)
     */
    public void verifySensorConfirm(byte[] sensorConfirm) throws PakeException
    {
        if (!sensorShareConsumed)
        {
            throw new PakeException("INVALID_PAKE_STATE: setSensorShare() must run first");
        }
        if (!role.verifyPeerConfirm(sensorConfirm))
        {
            throw new PakeException("KEY_CONFIRMATION_FAILED");
        }
        sensorVerified = true;
    }

    /**
     * The negotiated 16-byte AES session key. Only valid — and only MITM-authenticated —
     * after {@link #verifySensorConfirm} has succeeded.
     */
    public byte[] sessionKey()
    {
        if (!sensorVerified)
        {
            throw new IllegalStateException("session key requested before sensor confirmation verified");
        }
        return role.sessionKey();
    }

    // --- Typed attribute binding: split/assemble the 33-/32-byte values over the _1/_2 parts ---

    private static final int PART1_LEN = 20;

    /** {@code pake_client_share_1}: first 20 bytes of the compressed client share. */
    public PakeClientShare1 clientShare1() throws GeneralSecurityException
    {
        byte[] s = clientShare();
        return new PakeClientShare1(Arrays.copyOfRange(s, 0, PART1_LEN));
    }

    /** {@code pake_client_share_2}: remaining bytes of the compressed client share. */
    public PakeClientShare2 clientShare2() throws GeneralSecurityException
    {
        byte[] s = clientShare();
        return new PakeClientShare2(Arrays.copyOfRange(s, PART1_LEN, s.length));
    }

    /** Consumes the two {@code pake_sensor_share} parts (reassembled, then unmasked). */
    public void setSensorShare(PakeSensorShare1 part1, PakeSensorShare2 part2) throws GeneralSecurityException
    {
        setSensorShare(concat(part1.getRawData(), part2.getRawData()));
    }

    /** {@code pake_client_confirm_1}: first 20 bytes of the client confirmation MAC. */
    public PakeClientConfirm1 clientConfirm1() throws GeneralSecurityException
    {
        byte[] c = clientConfirm();
        return new PakeClientConfirm1(Arrays.copyOfRange(c, 0, PART1_LEN));
    }

    /** {@code pake_client_confirm_2}: remaining bytes of the client confirmation MAC. */
    public PakeClientConfirm2 clientConfirm2() throws GeneralSecurityException
    {
        byte[] c = clientConfirm();
        return new PakeClientConfirm2(Arrays.copyOfRange(c, PART1_LEN, c.length));
    }

    /** Verifies the two {@code pake_sensor_confirm} parts (reassembled). Throws on mismatch. */
    public void verifySensorConfirm(PakeSensorConfirm1 part1, PakeSensorConfirm2 part2) throws PakeException
    {
        verifySensorConfirm(concat(part1.getRawData(), part2.getRawData()));
    }

    private static byte[] concat(byte[] a, byte[] b)
    {
        byte[] out = Arrays.copyOf(a, a.length + b.length);
        System.arraycopy(b, 0, out, a.length, b.length);
        return out;
    }
}
