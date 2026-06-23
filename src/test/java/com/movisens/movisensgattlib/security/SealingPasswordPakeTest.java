package com.movisens.movisensgattlib.security;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

import org.junit.Test;

import com.movisens.movisensgattlib.security.MockedSpakeSensor.Attr;

/**
 * Exercises the second PAKE use case from the sealing design: the same
 * balanced-SPAKE2 handshake, but with the stored <em>sealing password</em> as the
 * shared secret instead of the onboarding colour code.
 *
 * <p>The crypto path ({@link SpakePairingClient} / {@link Spake2Role}) is secret-agnostic
 * — the secret is just {@code byte[]} — so these tests confirm that a sealing-password
 * secret pairs end-to-end over the GATT attribute interface and that a wrong password is
 * rejected with the session key withheld.</p>
 *
 * <p>The password→bytes encoding used here (US-ASCII) is the test's own convention; the
 * production encoding must be agreed byte-for-byte with the firmware (open point).</p>
 */
public class SealingPasswordPakeTest
{
    private static final byte[] SENSOR_ID = "sensor".getBytes(StandardCharsets.US_ASCII);
    private static final byte[] CLIENT_ID = "client".getBytes(StandardCharsets.US_ASCII);

    private static byte[] password(String s)
    {
        return s.getBytes(StandardCharsets.US_ASCII);
    }

    private final SecureRandom rng = new SecureRandom();

    @Test
    public void pairsWithCorrectSealingPassword() throws Exception
    {
        byte[] sealingPassword = password("Tr0ub4dor&3");

        SpakePairingClient client = new SpakePairingClient(SENSOR_ID, CLIENT_ID, sealingPassword, rng);
        MockedSpakeSensor sensor = new MockedSpakeSensor(sealingPassword, SENSOR_ID, CLIENT_ID, rng);

        sensor.write(Attr.CLIENT_SHARE, client.clientShare());
        client.setSensorShare(sensor.read(Attr.SENSOR_SHARE));
        sensor.write(Attr.CLIENT_CONFIRM, client.clientConfirm());
        client.verifySensorConfirm(sensor.read(Attr.SENSOR_CONFIRM));

        assertEquals(16, client.sessionKey().length);
        assertArrayEquals(client.sessionKey(), sensor.sessionKey());
    }

    @Test
    public void wrongSealingPasswordIsRejectedAndKeyIsWithheld() throws Exception
    {
        SpakePairingClient client = new SpakePairingClient(SENSOR_ID, CLIENT_ID, password("correct horse"), rng);
        MockedSpakeSensor sensor = new MockedSpakeSensor(password("wrong horse"), SENSOR_ID, CLIENT_ID, rng);

        sensor.write(Attr.CLIENT_SHARE, client.clientShare());
        client.setSensorShare(sensor.read(Attr.SENSOR_SHARE));

        // Sensor rejects the client confirm derived from a different password.
        try
        {
            sensor.write(Attr.CLIENT_CONFIRM, client.clientConfirm());
            fail("sensor must reject the client confirm for a wrong sealing password");
        }
        catch (PakeException expected)
        {
            assertEquals("KEY_CONFIRMATION_FAILED", expected.getMessage());
        }

        // No verified sensor confirm -> session key stays withheld.
        try
        {
            client.sessionKey();
            fail("session key must be withheld until the sealing password is verified");
        }
        catch (IllegalStateException expected)
        {
        }
    }

    @Test
    public void onboardingAndSealingUseDistinctKeysForDistinctSecrets() throws Exception
    {
        // Same identities, two different secrets (colour code vs sealing password)
        // must yield independent session keys — the two PAKE runs are not linkable.
        byte[] colourCode = {0, 1, 2, 2, 4, 3};
        byte[] sealingPassword = password("Tr0ub4dor&3");

        byte[] onboardingKey = run(colourCode);
        byte[] sealingKey = run(sealingPassword);

        assertEquals(16, onboardingKey.length);
        assertEquals(16, sealingKey.length);
        if (java.util.Arrays.equals(onboardingKey, sealingKey))
        {
            fail("distinct secrets must not produce the same session key");
        }
    }

    private byte[] run(byte[] secret) throws Exception
    {
        SpakePairingClient client = new SpakePairingClient(SENSOR_ID, CLIENT_ID, secret, rng);
        MockedSpakeSensor sensor = new MockedSpakeSensor(secret, SENSOR_ID, CLIENT_ID, rng);

        sensor.write(Attr.CLIENT_SHARE, client.clientShare());
        client.setSensorShare(sensor.read(Attr.SENSOR_SHARE));
        sensor.write(Attr.CLIENT_CONFIRM, client.clientConfirm());
        client.verifySensorConfirm(sensor.read(Attr.SENSOR_CONFIRM));
        return client.sessionKey();
    }
}
