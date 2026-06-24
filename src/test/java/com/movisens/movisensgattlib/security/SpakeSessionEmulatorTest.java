package com.movisens.movisensgattlib.security;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.security.SecureRandom;
import java.util.Arrays;

import org.junit.Test;

import com.movisens.movisensgattlib.attributes.EnumCommandResult;
import com.movisens.movisensgattlib.attributes.SealSensor;
import com.movisens.smartgattlib.helper.GattByteBuffer;
import com.movisens.smartgattlib.security.CryptoManager;

/**
 * Drives {@link SpakeSession} against the reusable {@link SpakeSensorEmulator} via
 * {@link MockSpakeBleConnection} — the template for the per-consumer emulator tests. Covers
 * onboarding, sealed access, a wrong secret, the rate-limit lockout tiers, and access control.
 */
public class SpakeSessionEmulatorTest
{
    private static final String ADVERTISED_NAME = "movisens Sensor 1234567890";
    private static final byte[] CLIENT_ID = SpakeIdentities.clientId();

    private final SecureRandom rng = new SecureRandom();

    private static byte[] colourCode()
    {
        return PairingColour.toSecret(Arrays.asList(
            PairingColour.RED, PairingColour.GREEN, PairingColour.BLUE,
            PairingColour.RED, PairingColour.GREEN, PairingColour.BLUE));
    }

    private SpakeSensorEmulator emulator(byte[] secret, boolean sealed, SensorClock clock)
    {
        byte[] serial = MockSpakeBleConnection.serialFrom(ADVERTISED_NAME);
        return new SpakeSensorEmulator(secret, serial, CLIENT_ID, sealed, rng, clock);
    }

    @Test
    public void onboardingWithColourCodeYieldsTheSharedKey() throws Exception
    {
        byte[] secret = colourCode();
        MockSpakeBleConnection connection =
            new MockSpakeBleConnection(emulator(secret, false, new SensorClock.Mutable()), ADVERTISED_NAME);

        byte[] aesKey = SpakeSession.run(connection, connection.getSensorSerial(), CLIENT_ID, secret);

        assertEquals(16, aesKey.length);
        assertArrayEquals(connection.sensorSessionKey(), aesKey);
    }

    @Test
    public void sealedAccessWithSealingKeyYieldsTheSharedKey() throws Exception
    {
        byte[] secret = sealingSecret("Tr0ub4dor&3");
        MockSpakeBleConnection connection =
            new MockSpakeBleConnection(emulator(secret, true, new SensorClock.Mutable()), ADVERTISED_NAME);

        byte[] aesKey = SpakeSession.run(connection, connection.getSensorSerial(), CLIENT_ID, secret);

        assertEquals(16, aesKey.length);
        assertArrayEquals(connection.sensorSessionKey(), aesKey);
    }

    @Test
    public void wrongSecretFailsWithKeyConfirmationFailedAndWithholdsKey() throws Exception
    {
        byte[] emulatorSecret = sealingSecret("correct horse");
        byte[] clientSecret = sealingSecret("wrong horse");
        MockSpakeBleConnection connection =
            new MockSpakeBleConnection(emulator(emulatorSecret, true, new SensorClock.Mutable()), ADVERTISED_NAME);

        try
        {
            SpakeSession.run(connection, connection.getSensorSerial(), CLIENT_ID, clientSecret);
            fail("wrong secret must not yield a session key");
        }
        catch (PakeException expected)
        {
            assertEquals(EnumCommandResult.KEY_CONFIRMATION_FAILED, expected.getResult());
        }
    }

    @Test
    public void rateLimitLocksAfterThreeFailuresAndReleasesWhenTheClockAdvances() throws Exception
    {
        byte[] emulatorSecret = sealingSecret("correct horse");
        byte[] wrong = sealingSecret("wrong horse");
        byte[] right = sealingSecret("correct horse");
        SensorClock.Mutable clock = new SensorClock.Mutable();
        SpakeSensorEmulator emulator = emulator(emulatorSecret, true, clock);

        // The first two wrong attempts are counted but grant free retries: no lockout yet.
        expectFailure(emulator, wrong, EnumCommandResult.KEY_CONFIRMATION_FAILED);
        expectFailure(emulator, wrong, EnumCommandResult.KEY_CONFIRMATION_FAILED);
        // The 3rd wrong attempt arms the 60-min lockout.
        expectFailure(emulator, wrong, EnumCommandResult.KEY_CONFIRMATION_FAILED);

        // While locked, even the correct secret is refused with the tier's rate-limit code.
        expectFailure(emulator, right, EnumCommandResult.PAKE_RATE_LIMITED_60_MIN);

        // After the lockout expires the correct secret succeeds (a success resets the counter).
        clock.advance(60L * 60_000 + 1);
        MockSpakeBleConnection connection = new MockSpakeBleConnection(emulator, ADVERTISED_NAME);
        byte[] aesKey = SpakeSession.run(connection, connection.getSensorSerial(), CLIENT_ID, right);
        assertEquals(16, aesKey.length);
    }

    @Test
    public void rateLimitTiersEscalateEveryThreeFailures() throws Exception
    {
        byte[] secret = sealingSecret("correct horse");
        byte[] wrong = sealingSecret("wrong horse");
        SensorClock.Mutable clock = new SensorClock.Mutable();
        SpakeSensorEmulator emulator = emulator(secret, true, clock);

        // Each tier is armed by the 3rd failure of its block (two free retries precede it); the
        // 24-h cap then repeats for every further block of 3 failures.
        EnumCommandResult[] tierCode = {
            EnumCommandResult.PAKE_RATE_LIMITED_60_MIN,
            EnumCommandResult.PAKE_RATE_LIMITED_2_H,
            EnumCommandResult.PAKE_RATE_LIMITED_4_H,
            EnumCommandResult.PAKE_RATE_LIMITED_8_H,
            EnumCommandResult.PAKE_RATE_LIMITED_24_H,
            EnumCommandResult.PAKE_RATE_LIMITED_24_H // cap repeats
        };
        long[] tierMillis = {
            60L * 60_000, 2L * 60L * 60_000, 4L * 60L * 60_000, 8L * 60L * 60_000,
            24L * 60L * 60_000, 24L * 60L * 60_000
        };

        for (int tier = 0; tier < tierCode.length; tier++)
        {
            // Two free retries, then the 3rd failure of the block arms this tier's lockout.
            expectFailure(emulator, wrong, EnumCommandResult.KEY_CONFIRMATION_FAILED);
            expectFailure(emulator, wrong, EnumCommandResult.KEY_CONFIRMATION_FAILED);
            expectFailure(emulator, wrong, EnumCommandResult.KEY_CONFIRMATION_FAILED);
            // Now locked: any further start is refused with this tier's code.
            expectFailure(emulator, wrong, tierCode[tier]);
            // Wait out the lockout so the next block of failures can proceed.
            clock.advance(tierMillis[tier] + 1);
        }
    }

    @Test
    public void protectedWriteIsDeniedBeforeHandshakeAndAllowedAfter() throws Exception
    {
        byte[] secret = colourCode();
        CryptoManager cryptoManager = new CryptoManager();
        cryptoManager.initialize();
        MockSpakeBleConnection connection =
            new MockSpakeBleConnection(emulator(secret, false, new SensorClock.Mutable()), ADVERTISED_NAME);

        // A protected SealSensor write before any handshake is denied.
        cryptoManager.setKey(new byte[16]); // SealSensor requires an encrypted session to build
        assertEquals(EnumCommandResult.ACCESS_DENIED,
            connection.setAttribute(new SealSensor(cryptoManager, "newpw")));
        assertFalse(connection.getAttribute(com.movisens.movisensgattlib.MovisensCharacteristics.SENSOR_SEALED).getValue());

        // After a successful handshake the protected write is accepted and seals the sensor.
        byte[] aesKey = SpakeSession.run(connection, connection.getSensorSerial(), CLIENT_ID, secret);
        cryptoManager.setKey(aesKey);
        assertEquals(EnumCommandResult.OK,
            connection.setAttribute(new SealSensor(cryptoManager, "newpw")));
        assertTrue(connection.getAttribute(com.movisens.movisensgattlib.MovisensCharacteristics.SENSOR_SEALED).getValue());
    }

    // --- helpers --------------------------------------------------------------------------------

    private void expectFailure(SpakeSensorEmulator emulator, byte[] secret, EnumCommandResult expected)
        throws Exception
    {
        MockSpakeBleConnection connection = new MockSpakeBleConnection(emulator, ADVERTISED_NAME);
        try
        {
            SpakeSession.run(connection, connection.getSensorSerial(), CLIENT_ID, secret);
            fail("expected failure " + expected);
        }
        catch (PakeException e)
        {
            assertEquals(expected, e.getResult());
        }
    }

    /** Sealing-password secret: the 8 bytes of the derived key (same bytes used by both sides). */
    private static byte[] sealingSecret(String password) throws Exception
    {
        long key = com.movisens.smartgattlib.security.KeyGenerator.createKey(password);
        return GattByteBuffer.allocate(8).putInt64(key).array();
    }
}
