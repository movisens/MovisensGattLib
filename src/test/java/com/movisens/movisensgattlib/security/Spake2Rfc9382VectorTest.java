package com.movisens.movisensgattlib.security;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

import org.junit.Test;

/**
 * Known-answer test against RFC 9382 Appendix B, first vector
 * (ciphersuite P256-SHA256-HKDF-HMAC, A='server', B='client'). Anchors the
 * {@link Spake2Role} construction to the standard rather than to itself.
 */
public class Spake2Rfc9382VectorTest
{
    private static final byte[] ID_A = "server".getBytes(StandardCharsets.US_ASCII);
    private static final byte[] ID_B = "client".getBytes(StandardCharsets.US_ASCII);

    private static final String W =
        "2ee57912099d31560b3a44b1184b9b4866e904c49d12ac5042c97dca461b1a5f";
    private static final String X =
        "43dd0fd7215bdcb482879fca3220c6a968e66d70b1356cac18bb26c84a78d729";
    private static final String Y =
        "dcb60106f276b02606d8ef0a328c02e4b629f84f89786af5befb0bc75b6e66be";
    private static final String K =
        "0412af7e89717850671913e6b469ace67bd90a4df8ce45c2af19010175e37eed"
        + "69f75897996d539356e2fa6a406d528501f907e04d97515fbe83db277b715d3325";
    private static final String KE = "0e0672dc86f8e45565d338b0540abe69";
    private static final String CA =
        "58ad4aa88e0b60d5061eb6b5dd93e80d9c4f00d127c65b3b35b1b5281fee38f0";
    private static final String CB =
        "d3e2e547f1ae04f2dbdbf0fc4b79f8ecff2dff314b5d32fe9fcef2fb26dc459b";

    @Test
    public void reproducesRfc9382FirstVector() throws Exception
    {
        SecureRandom rng = new SecureRandom();
        BigInteger w = new BigInteger(1, hex(W));

        Spake2Role a = new Spake2Role(Spake2Role.Role.A, ID_A, ID_B, w, rng);
        Spake2Role b = new Spake2Role(Spake2Role.Role.B, ID_A, ID_B, w, rng);

        // Drive the handshake with self-computed shares (inputs are w, x, y);
        // the KAT gates on the derived outputs Ke, cA, cB.
        byte[] pA = a.createShareWithScalar(new BigInteger(1, hex(X)));
        byte[] pB = b.createShareWithScalar(new BigInteger(1, hex(Y)));

        a.setPeerShare(pB);
        b.setPeerShare(pA);

        assertArrayEquals("K (A)", hex(K), a.sharedSecretPoint());
        assertArrayEquals("K (B)", hex(K), b.sharedSecretPoint());
        assertArrayEquals("Ke (A)", hex(KE), a.sessionKey());
        assertArrayEquals("Ke (B)", hex(KE), b.sessionKey());
        assertArrayEquals("cA", hex(CA), a.ownConfirm());
        assertArrayEquals("cB", hex(CB), b.ownConfirm());

        assertTrue(a.verifyPeerConfirm(b.ownConfirm()));
        assertTrue(b.verifyPeerConfirm(a.ownConfirm()));
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
