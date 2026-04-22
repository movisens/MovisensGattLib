package com.movisens.movisensgattlib.security;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECFieldFp;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.EllipticCurve;
import java.util.Arrays;

import org.junit.Test;

public class Secp256r1PointCodecWithoutBcTest
{
    private static final byte[] GENERATOR_COMPRESSED = hex(
        "036b17d1f2e12c4247f8bce6e563a440f277037d812deb33a0f4a13945d898c296"
    );

    private static final BigInteger GENERATOR_X = hexBigInteger(
        "6b17d1f2e12c4247f8bce6e563a440f277037d812deb33a0f4a13945d898c296"
    );

    private static final BigInteger GENERATOR_Y = hexBigInteger(
        "4fe342e2fe1a7f9b8ee7eb4a7c0f9e162bce33576b315ececbb6406837bf51f5"
    );

    private static final byte[] INVALID_X_EQUAL_TO_PRIME = hex(
        "02ffffffff00000001000000000000000000000000ffffffffffffffffffffffff"
    );

    @Test
    public void encodeCompressedShouldEncodeGeneratorPoint() throws Exception
    {
        ECPublicKey publicKey = createPublicKey(GENERATOR_X, GENERATOR_Y);
        assertArrayEquals(GENERATOR_COMPRESSED, Secp256r1PointCodec.encodeCompressed(publicKey));
    }

    @Test
    public void decodeCompressedShouldDecodeGeneratorPoint() throws Exception
    {
        ECPublicKey publicKey = Secp256r1PointCodec.decodeCompressed(GENERATOR_COMPRESSED);

        assertEquals(GENERATOR_X, publicKey.getW().getAffineX());
        assertEquals(GENERATOR_Y, publicKey.getW().getAffineY());
        assertArrayEquals(GENERATOR_COMPRESSED, Secp256r1PointCodec.encodeCompressed(publicKey));
    }

    @Test
    public void decodeCompressedShouldRejectWrongLength() throws Exception
    {
        assertDecodeFails(Arrays.copyOf(GENERATOR_COMPRESSED, Secp256r1PointCodec.COMPRESSED_LEN - 1));
    }

    @Test
    public void decodeCompressedShouldRejectWrongPrefix() throws Exception
    {
        byte[] invalid = Arrays.copyOf(GENERATOR_COMPRESSED, GENERATOR_COMPRESSED.length);
        invalid[0] = 0x04;

        assertDecodeFails(invalid);
    }

    @Test
    public void decodeCompressedShouldRejectXEqualToPrime() throws Exception
    {
        assertDecodeFails(INVALID_X_EQUAL_TO_PRIME);
    }

    @Test
    public void decodeCompressedShouldRejectPointWithoutSquareRoot() throws Exception
    {
        assertDecodeFails(findCompressedPointWithoutSquareRoot());
    }

    private static ECPublicKey createPublicKey(BigInteger x, BigInteger y) throws GeneralSecurityException
    {
        ECParameterSpec parameterSpec = Secp256r1Support.loadParams();
        ECPublicKeySpec publicKeySpec = new ECPublicKeySpec(new ECPoint(x, y), parameterSpec);
        return (ECPublicKey) KeyFactory.getInstance("EC").generatePublic(publicKeySpec);
    }

    private static byte[] findCompressedPointWithoutSquareRoot() throws GeneralSecurityException
    {
        ECParameterSpec parameterSpec = Secp256r1Support.loadParams();
        EllipticCurve curve = parameterSpec.getCurve();
        BigInteger prime = ((ECFieldFp) curve.getField()).getP();
        BigInteger exponent = prime.subtract(BigInteger.ONE).shiftRight(1);

        for (int candidate = 0; candidate < 1024; candidate++) {
            BigInteger x = BigInteger.valueOf(candidate);
            BigInteger rhs = x.modPow(BigInteger.valueOf(3L), prime)
                .add(curve.getA().multiply(x))
                .add(curve.getB())
                .mod(prime);

            if (!rhs.equals(BigInteger.ZERO) && !rhs.modPow(exponent, prime).equals(BigInteger.ONE)) {
                return compressedPoint((byte) 0x02, x);
            }
        }

        fail("Unable to find invalid compressed point for secp256r1");
        return null;
    }

    private static byte[] compressedPoint(byte prefix, BigInteger x)
    {
        byte[] point = new byte[Secp256r1PointCodec.COMPRESSED_LEN];
        point[0] = prefix;

        byte[] xBytes = x.toByteArray();
        int offset = xBytes.length > 1 && xBytes[0] == 0 ? 1 : 0;
        int length = xBytes.length - offset;
        System.arraycopy(xBytes, offset, point, point.length - length, length);

        return point;
    }

    private static void assertDecodeFails(byte[] compressedPoint) throws Exception
    {
        try {
            Secp256r1PointCodec.decodeCompressed(compressedPoint);
            fail("Expected GeneralSecurityException");
        } catch (GeneralSecurityException expected) {
        }
    }

    private static BigInteger hexBigInteger(String value)
    {
        return new BigInteger(value, 16);
    }

    private static byte[] hex(String value)
    {
        byte[] result = new byte[value.length() / 2];

        for (int i = 0; i < result.length; i++) {
            int index = i * 2;
            result[i] = (byte) Integer.parseInt(value.substring(index, index + 2), 16);
        }

        return result;
    }
}
