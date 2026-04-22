package com.movisens.movisensgattlib.security;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.security.KeyPair;
import java.security.SecureRandom;
import java.security.interfaces.ECPublicKey;

import org.junit.Test;

public class Secp256r1PointCodecRoundtripTest
{
    private static final String ITERATIONS_PROPERTY = "movisens.secp256r1PointCodec.iterations";
    private static final String ITERATIONS_ENV = "MOVISENS_SECP256R1_POINT_CODEC_ITERATIONS";
    private static final int DEFAULT_ITERATIONS = 10000;

    @Test
    public void encodeAndDecodeShouldRoundtripForConfiguredNumberOfGeneratedKeys() throws Exception
    {
        int iterations = getIterations();
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(new byte[] { 0x45, 0x23, 0x11, 0x09, 0x5A, 0x33, 0x7C, 0x01 });

        for (int i = 0; i < iterations; i++) {
            KeyPair keyPair = Secp256r1Support.generateKeyPair(secureRandom);
            ECPublicKey publicKey = (ECPublicKey) keyPair.getPublic();

            byte[] compressed = Secp256r1PointCodec.encodeCompressed(publicKey);
            ECPublicKey decodedPublicKey = Secp256r1PointCodec.decodeCompressed(compressed);

            assertEquals("Compressed point length mismatch at iteration " + i, Secp256r1PointCodec.COMPRESSED_LEN, compressed.length);
            assertTrue(
                "Compressed point prefix mismatch at iteration " + i,
                compressed[0] == 0x02 || compressed[0] == 0x03
            );
            assertArrayEquals(
                "Compressed roundtrip mismatch at iteration " + i,
                compressed,
                Secp256r1PointCodec.encodeCompressed(decodedPublicKey)
            );
            assertEquals(
                "Affine X mismatch at iteration " + i,
                publicKey.getW().getAffineX(),
                decodedPublicKey.getW().getAffineX()
            );
            assertEquals(
                "Affine Y mismatch at iteration " + i,
                publicKey.getW().getAffineY(),
                decodedPublicKey.getW().getAffineY()
            );
        }
    }

    private static int getIterations()
    {
        Integer systemPropertyValue = Integer.getInteger(ITERATIONS_PROPERTY);
        if (systemPropertyValue != null) {
            return systemPropertyValue;
        }

        String environmentValue = System.getenv(ITERATIONS_ENV);
        if (environmentValue != null && !environmentValue.trim().isEmpty()) {
            return Integer.parseInt(environmentValue.trim());
        }

        return DEFAULT_ITERATIONS;
    }
}
