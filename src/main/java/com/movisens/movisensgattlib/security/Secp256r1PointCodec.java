package com.movisens.movisensgattlib.security;

import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECField;
import java.security.spec.ECFieldFp;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.EllipticCurve;

final class Secp256r1PointCodec
{
    static final int COMPRESSED_LEN = 33;

    private static final int COORD_LEN = 32;
    private static final BigInteger THREE = BigInteger.valueOf(3L);

    private Secp256r1PointCodec()
    {
    }

    static byte[] encodeCompressed(ECPublicKey publicKey) throws GeneralSecurityException
    {
        if (publicKey == null) {
            throw new GeneralSecurityException("Public key must not be null");
        }

        ECPoint point = publicKey.getW();
        validatePoint(point, publicKey.getParams());

        byte[] encoded = new byte[COMPRESSED_LEN];
        encoded[0] = (byte) (point.getAffineY().testBit(0) ? 0x03 : 0x02);

        byte[] x = toUnsignedFixedLength(point.getAffineX(), COORD_LEN);
        System.arraycopy(x, 0, encoded, 1, x.length);

        return encoded;
    }

    static ECPublicKey decodeCompressed(byte[] compressed) throws GeneralSecurityException
    {
        if (compressed == null) {
            throw new GeneralSecurityException("Compressed point must not be null");
        }
        if (compressed.length != COMPRESSED_LEN) {
            throw new GeneralSecurityException("Incorrect length for compressed point");
        }

        int prefix = compressed[0] & 0xFF;
        if (prefix != 0x02 && prefix != 0x03) {
            throw new GeneralSecurityException("Invalid point prefix");
        }

        ECParameterSpec parameterSpec = Secp256r1Support.loadParams();
        EllipticCurve curve = parameterSpec.getCurve();
        ECField field = curve.getField();
        if (!(field instanceof ECFieldFp)) {
            throw new GeneralSecurityException("Only prime field curves are supported");
        }

        BigInteger prime = ((ECFieldFp) field).getP();
        BigInteger x = new BigInteger(1, java.util.Arrays.copyOfRange(compressed, 1, compressed.length));
        if (x.compareTo(prime) >= 0) {
            throw new GeneralSecurityException("X coordinate out of range");
        }

        BigInteger rightHandSide = x.modPow(THREE, prime)
            .add(curve.getA().multiply(x))
            .add(curve.getB())
            .mod(prime);

        BigInteger y = modSqrt(rightHandSide, prime);
        boolean expectOddY = prefix == 0x03;
        if (y.testBit(0) != expectOddY) {
            y = prime.subtract(y).mod(prime);
        }
        if (y.testBit(0) != expectOddY) {
            throw new GeneralSecurityException("Invalid point compression");
        }

        ECPoint point = new ECPoint(x, y);
        validatePoint(point, parameterSpec);

        KeyFactory keyFactory = KeyFactory.getInstance("EC");
        return (ECPublicKey) keyFactory.generatePublic(new ECPublicKeySpec(point, parameterSpec));
    }

    private static void validatePoint(ECPoint point, ECParameterSpec parameterSpec) throws GeneralSecurityException
    {
        if (point == null || parameterSpec == null) {
            throw new GeneralSecurityException("Point parameters must not be null");
        }
        if (ECPoint.POINT_INFINITY.equals(point)) {
            throw new GeneralSecurityException("Point at infinity is not supported");
        }

        EllipticCurve curve = parameterSpec.getCurve();
        ECField field = curve.getField();
        if (!(field instanceof ECFieldFp)) {
            throw new GeneralSecurityException("Only prime field curves are supported");
        }

        BigInteger prime = ((ECFieldFp) field).getP();
        BigInteger x = point.getAffineX();
        BigInteger y = point.getAffineY();

        if (x == null || y == null) {
            throw new GeneralSecurityException("Affine coordinates must not be null");
        }
        if (x.signum() < 0 || x.compareTo(prime) >= 0 || y.signum() < 0 || y.compareTo(prime) >= 0) {
            throw new GeneralSecurityException("Point coordinates out of range");
        }

        BigInteger leftHandSide = y.multiply(y).mod(prime);
        BigInteger rightHandSide = x.modPow(THREE, prime)
            .add(curve.getA().multiply(x))
            .add(curve.getB())
            .mod(prime);

        if (!leftHandSide.equals(rightHandSide)) {
            throw new GeneralSecurityException("Point is not on secp256r1");
        }
    }

    private static BigInteger modSqrt(BigInteger value, BigInteger prime) throws GeneralSecurityException
    {
        BigInteger normalized = value.mod(prime);
        BigInteger candidate = normalized.modPow(prime.add(BigInteger.ONE).shiftRight(2), prime);
        if (!candidate.multiply(candidate).mod(prime).equals(normalized)) {
            throw new GeneralSecurityException("Invalid point compression");
        }
        return candidate;
    }

    private static byte[] toUnsignedFixedLength(BigInteger value, int size) throws GeneralSecurityException
    {
        if (value == null || value.signum() < 0) {
            throw new GeneralSecurityException("Coordinate must be positive");
        }

        byte[] raw = value.toByteArray();
        int offset = raw.length > 1 && raw[0] == 0 ? 1 : 0;
        int length = raw.length - offset;
        if (length > size) {
            throw new GeneralSecurityException("Coordinate does not fit expected size");
        }

        byte[] padded = new byte[size];
        System.arraycopy(raw, offset, padded, size - length, length);
        return padded;
    }
}
