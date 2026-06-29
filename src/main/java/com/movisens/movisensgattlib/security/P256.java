package com.movisens.movisensgattlib.security;

import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.spec.ECFieldFp;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.EllipticCurve;

/**
 * Minimal, BouncyCastle-free secp256r1 (P-256) point arithmetic for the balanced
 * SPAKE2 handshake. Built on the pure-JCE curve parameters from
 * {@link Secp256r1Support}; affine math over {@link BigInteger}.
 *
 * <p>NOT constant-time. This is acceptable on the smartphone side: the physically
 * attacked target is the sensor, not the phone. The sensor side uses the constant-time
 * mbedTLS construction instead.</p>
 */
final class P256
{
    static final ECParameterSpec PARAMS;
    static final EllipticCurve CURVE;
    static final BigInteger P;        // field prime
    static final BigInteger A;        // curve coefficient a (== p-3 for P-256)
    static final BigInteger B;        // curve coefficient b
    static final BigInteger N;        // group order
    static final ECPoint G;           // generator
    static final ECPoint M;           // SPAKE2 seed point M (RFC 9382, P-256)
    static final ECPoint N_POINT;     // SPAKE2 seed point N (RFC 9382, P-256)

    static final int UNCOMPRESSED_LEN = 65;
    static final int COMPRESSED_LEN = 33;

    private static final int COORD_LEN = 32;
    private static final BigInteger THREE = BigInteger.valueOf(3L);

    static
    {
        try
        {
            PARAMS = Secp256r1Support.loadParams();
            CURVE = PARAMS.getCurve();
            P = ((ECFieldFp) CURVE.getField()).getP();
            A = CURVE.getA().mod(P);
            B = CURVE.getB().mod(P);
            N = PARAMS.getOrder();
            G = PARAMS.getGenerator();
            // RFC 9382, Section 4: fixed seed points for P-256 (compressed SEC1).
            M = Secp256r1PointCodec
                .decodeCompressed(hex("02886e2f97ace46e55ba9dd7242579f2993b64e16ef3dcab95afd497333d8fa12f"))
                .getW();
            N_POINT = Secp256r1PointCodec
                .decodeCompressed(hex("03d8bbd6c639c62937b04d997f38c3770719c629d7014d49a24b4f98baa1292b49"))
                .getW();
        }
        catch (GeneralSecurityException e)
        {
            throw new ExceptionInInitializerError(e);
        }
    }

    private P256()
    {
    }

    static ECPoint negate(ECPoint point)
    {
        if (point == ECPoint.POINT_INFINITY)
        {
            return point;
        }
        return new ECPoint(point.getAffineX(), P.subtract(point.getAffineY()).mod(P));
    }

    static ECPoint add(ECPoint p1, ECPoint p2)
    {
        if (p1 == ECPoint.POINT_INFINITY)
        {
            return p2;
        }
        if (p2 == ECPoint.POINT_INFINITY)
        {
            return p1;
        }

        BigInteger x1 = p1.getAffineX();
        BigInteger y1 = p1.getAffineY();
        BigInteger x2 = p2.getAffineX();
        BigInteger y2 = p2.getAffineY();

        if (x1.equals(x2))
        {
            if (y1.add(y2).mod(P).signum() == 0)
            {
                return ECPoint.POINT_INFINITY;
            }
            return doublePoint(p1);
        }

        BigInteger lambda = y2.subtract(y1).multiply(x2.subtract(x1).modInverse(P)).mod(P);
        BigInteger x3 = lambda.multiply(lambda).subtract(x1).subtract(x2).mod(P);
        BigInteger y3 = lambda.multiply(x1.subtract(x3)).subtract(y1).mod(P);
        return new ECPoint(x3, y3);
    }

    static ECPoint doublePoint(ECPoint point)
    {
        if (point == ECPoint.POINT_INFINITY)
        {
            return point;
        }

        BigInteger x = point.getAffineX();
        BigInteger y = point.getAffineY();
        if (y.signum() == 0)
        {
            return ECPoint.POINT_INFINITY;
        }

        BigInteger numerator = x.multiply(x).multiply(THREE).add(A).mod(P);
        BigInteger lambda = numerator.multiply(y.shiftLeft(1).modInverse(P)).mod(P);
        BigInteger x3 = lambda.multiply(lambda).subtract(x.shiftLeft(1)).mod(P);
        BigInteger y3 = lambda.multiply(x.subtract(x3)).subtract(y).mod(P);
        return new ECPoint(x3, y3);
    }

    static ECPoint scalarMul(BigInteger k, ECPoint point)
    {
        BigInteger scalar = k.mod(N);
        ECPoint result = ECPoint.POINT_INFINITY;
        ECPoint addend = point;

        for (int i = 0; i < scalar.bitLength(); i++)
        {
            if (scalar.testBit(i))
            {
                result = add(result, addend);
            }
            addend = doublePoint(addend);
        }

        return result;
    }

    /** w = SHA-256(code) mod n. */
    static BigInteger hashToScalar(byte[] code) throws GeneralSecurityException
    {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return new BigInteger(1, md.digest(code)).mod(N);
    }

    /** SEC1 uncompressed encoding: 0x04 || X(32) || Y(32). */
    static byte[] encode(ECPoint point) throws GeneralSecurityException
    {
        if (point == ECPoint.POINT_INFINITY)
        {
            throw new GeneralSecurityException("cannot encode point at infinity");
        }

        byte[] encoded = new byte[UNCOMPRESSED_LEN];
        encoded[0] = 0x04;
        System.arraycopy(toFixed(point.getAffineX()), 0, encoded, 1, COORD_LEN);
        System.arraycopy(toFixed(point.getAffineY()), 0, encoded, 1 + COORD_LEN, COORD_LEN);
        return encoded;
    }

    /** Decodes and validates a SEC1 uncompressed point (on-curve, in range, not infinity). */
    static ECPoint decode(byte[] encoded) throws GeneralSecurityException
    {
        if (encoded == null || encoded.length != UNCOMPRESSED_LEN || encoded[0] != 0x04)
        {
            throw new GeneralSecurityException("invalid uncompressed point encoding");
        }

        BigInteger x = new BigInteger(1, java.util.Arrays.copyOfRange(encoded, 1, 1 + COORD_LEN));
        BigInteger y = new BigInteger(1, java.util.Arrays.copyOfRange(encoded, 1 + COORD_LEN, UNCOMPRESSED_LEN));

        if (x.compareTo(P) >= 0 || y.compareTo(P) >= 0)
        {
            throw new GeneralSecurityException("point coordinate out of range");
        }

        BigInteger lhs = y.multiply(y).mod(P);
        BigInteger rhs = x.modPow(THREE, P).add(A.multiply(x)).add(B).mod(P);
        if (!lhs.equals(rhs))
        {
            throw new GeneralSecurityException("point is not on secp256r1");
        }

        return new ECPoint(x, y);
    }

    /** SEC1 compressed encoding: (0x02|0x03) || X(32). */
    static byte[] encodeCompressed(ECPoint point) throws GeneralSecurityException
    {
        if (point == ECPoint.POINT_INFINITY)
        {
            throw new GeneralSecurityException("cannot encode point at infinity");
        }
        byte[] encoded = new byte[COMPRESSED_LEN];
        encoded[0] = (byte) (point.getAffineY().testBit(0) ? 0x03 : 0x02);
        System.arraycopy(toFixed(point.getAffineX()), 0, encoded, 1, COORD_LEN);
        return encoded;
    }

    /** Decodes and validates a SEC1 compressed point (recovers Y via modular sqrt). */
    static ECPoint decodeCompressed(byte[] encoded) throws GeneralSecurityException
    {
        if (encoded == null || encoded.length != COMPRESSED_LEN)
        {
            throw new GeneralSecurityException("invalid compressed point length");
        }
        int prefix = encoded[0] & 0xFF;
        if (prefix != 0x02 && prefix != 0x03)
        {
            throw new GeneralSecurityException("invalid compressed point prefix");
        }

        BigInteger x = new BigInteger(1, java.util.Arrays.copyOfRange(encoded, 1, COMPRESSED_LEN));
        if (x.compareTo(P) >= 0)
        {
            throw new GeneralSecurityException("x coordinate out of range");
        }

        BigInteger rhs = x.modPow(THREE, P).add(A.multiply(x)).add(B).mod(P);
        BigInteger y = rhs.modPow(P.add(BigInteger.ONE).shiftRight(2), P); // p = 3 mod 4
        if (!y.multiply(y).mod(P).equals(rhs))
        {
            throw new GeneralSecurityException("point is not on secp256r1");
        }
        boolean wantOdd = prefix == 0x03;
        if (y.testBit(0) != wantOdd)
        {
            y = P.subtract(y);
        }
        return new ECPoint(x, y);
    }

    /** Wire helper: uncompressed (65) -> compressed (33), validating the point. */
    static byte[] compress(byte[] uncompressed) throws GeneralSecurityException
    {
        return encodeCompressed(decode(uncompressed));
    }

    /** Wire helper: compressed (33) -> uncompressed (65), validating the point. */
    static byte[] decompress(byte[] compressed) throws GeneralSecurityException
    {
        return encode(decodeCompressed(compressed));
    }

    private static byte[] toFixed(BigInteger value)
    {
        byte[] raw = value.toByteArray();
        byte[] out = new byte[COORD_LEN];
        int offset = raw.length > 1 && raw[0] == 0 ? 1 : 0;
        int length = raw.length - offset;
        System.arraycopy(raw, offset, out, COORD_LEN - length, length);
        return out;
    }

    private static byte[] hex(String value)
    {
        byte[] result = new byte[value.length() / 2];
        for (int i = 0; i < result.length; i++)
        {
            result[i] = (byte) Integer.parseInt(value.substring(i * 2, i * 2 + 2), 16);
        }
        return result;
    }
}
