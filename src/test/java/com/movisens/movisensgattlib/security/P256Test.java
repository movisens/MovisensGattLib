package com.movisens.movisensgattlib.security;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.spec.ECPoint;

import org.junit.Test;

public class P256Test
{
    @Test
    public void scalarMulByOrderIsInfinity()
    {
        assertSame(ECPoint.POINT_INFINITY, P256.scalarMul(P256.N, P256.G));
    }

    @Test
    public void scalarMulByOneIsIdentity()
    {
        assertEquals(P256.G, P256.scalarMul(BigInteger.ONE, P256.G));
    }

    @Test
    public void doublingMatchesScalarMulByTwo()
    {
        assertEquals(P256.doublePoint(P256.G), P256.scalarMul(BigInteger.valueOf(2L), P256.G));
        assertEquals(P256.add(P256.G, P256.G), P256.doublePoint(P256.G));
    }

    @Test
    public void addingInverseGivesInfinity()
    {
        assertSame(ECPoint.POINT_INFINITY, P256.add(P256.G, P256.negate(P256.G)));
    }

    @Test
    public void diffieHellmanIsCommutative()
    {
        BigInteger a = new BigInteger("1234567890abcdef1234567890abcdef", 16);
        BigInteger b = new BigInteger("fedcba0987654321fedcba0987654321", 16);

        ECPoint left = P256.scalarMul(a, P256.scalarMul(b, P256.G));
        ECPoint right = P256.scalarMul(b, P256.scalarMul(a, P256.G));
        assertEquals(left, right);
    }

    @Test
    public void seedPointsAreOnCurve() throws GeneralSecurityException
    {
        // decode() re-validates the on-curve property of M and N.
        assertEquals(P256.M, P256.decode(P256.encode(P256.M)));
        assertEquals(P256.N_POINT, P256.decode(P256.encode(P256.N_POINT)));
    }

    @Test
    public void encodeDecodeRoundtrip() throws GeneralSecurityException
    {
        ECPoint point = P256.scalarMul(BigInteger.valueOf(7L), P256.G);
        byte[] encoded = P256.encode(point);
        assertEquals(P256.UNCOMPRESSED_LEN, encoded.length);
        assertEquals(0x04, encoded[0] & 0xFF);
        assertEquals(point, P256.decode(encoded));
    }

    @Test
    public void compressedRoundtripMatchesUncompressed() throws GeneralSecurityException
    {
        ECPoint point = P256.scalarMul(BigInteger.valueOf(13L), P256.G);
        byte[] compressed = P256.encodeCompressed(point);
        assertEquals(P256.COMPRESSED_LEN, compressed.length);
        assertEquals(point, P256.decodeCompressed(compressed));
        // compress/decompress are inverse of encode/decode
        assertArrayEquals(P256.encode(point), P256.decompress(P256.compress(P256.encode(point))));
    }

    @Test
    public void decodeRejectsOffCurvePoint() throws GeneralSecurityException
    {
        byte[] bad = P256.encode(P256.G);
        bad[bad.length - 1] ^= 0x01; // perturb Y -> no longer on curve
        try
        {
            P256.decode(bad);
            fail("expected rejection of off-curve point");
        }
        catch (GeneralSecurityException expected)
        {
        }
    }
}
