package com.movisens.movisensgattlib.security;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Arrays;

import org.junit.Test;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.movisensgattlib.attributes.EnumCommandResult;
import com.movisens.movisensgattlib.attributes.SensorSealed;
import com.movisens.movisensgattlib.security.MockedSpakeSensor.Attr;
import com.movisens.smartgattlib.helper.AbstractAttribute;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.security.CryptoManager;

/**
 * Drives {@link SpakeManager} through a {@link MockedBleConnection} wrapper around
 * {@link MockedSpakeSensor}, the way an application drives the PAKE GATT flow:
 * {@code bleConnection.setAttribute(...)} to write request attributes and
 * {@code bleConnection.getAttribute(characteristic)} to read responses.
 *
 * <p>In the SPAKE2 scheme the handshake's key confirmation is itself the authentication,
 * so a wrong secret or a MITM surfaces as a non-OK command result / a withheld key. The
 * derived sealing-key bytes (sealed sensor) and the onboarding colour code (unsealed sensor)
 * are the same kind of {@code byte[]} secret, so one flow covers both.</p>
 */
public class SpakeManagerBleSchemeTest
{
    private static final byte[] CLIENT_ID = SpakeIdentities.clientId();
    private static final byte[] SEALING_SECRET = SealingPassword.toSecret("Tr0ub4dor&3");
    // 6-symbol LED code, encoded one byte per colour (PairingColour values 1..3 = red/green/blue).
    private static final byte[] COLOUR_CODE = PairingColour.toSecret(Arrays.asList(
        PairingColour.RED, PairingColour.GREEN, PairingColour.BLUE,
        PairingColour.RED, PairingColour.GREEN, PairingColour.BLUE));
    private static final String ADVERTISED_NAME = "movisens Sensor 1234567890";

    private final SecureRandom rng = new SecureRandom();

    // Sealed access and onboarding are the SAME flow; only the secret and the sealed flag differ.

    @Test
    public void sealedAccessThroughBleConnection() throws Exception
    {
        MockedBleConnection bleConnection = sealedConnectionWith(SEALING_SECRET);

        byte[] aesKey = runApplicationScheme(bleConnection, true, SEALING_SECRET);

        assertEquals(16, aesKey.length);
        assertArrayEquals(bleConnection.sensorSessionKey(), aesKey);
    }

    @Test
    public void onboardingWithColourCodeThroughBleConnection() throws Exception
    {
        MockedBleConnection bleConnection = unsealedConnectionWith(COLOUR_CODE);

        byte[] aesKey = runApplicationScheme(bleConnection, false, COLOUR_CODE);

        // After this the secure channel is up; the app would then write a SealSensor command to
        // set the sealing password (the seal state change is not modelled by MockedSpakeSensor).
        assertEquals(16, aesKey.length);
        assertArrayEquals(bleConnection.sensorSessionKey(), aesKey);
    }

    @Test
    public void wrongSealingPasswordSurfacesAsCommandResult() throws Exception
    {
        MockedBleConnection bleConnection = sealedConnectionWith(SEALING_SECRET);
        byte[] sensorId = bleConnection.getSensorSerial();
        SpakeManager spakeManager =
            new SpakeManager(sensorId, CLIENT_ID, SealingPassword.toSecret("wrong password"), rng);

        for (AbstractAttribute request : spakeManager.getShareRequestAttributes())
        {
            bleConnection.setAttribute(request);
        }
        spakeManager.setSensorShareResponse(new AbstractReadAttribute[] {
            bleConnection.getAttribute(MovisensCharacteristics.PAKE_SENSOR_SHARE_1),
            bleConnection.getAttribute(MovisensCharacteristics.PAKE_SENSOR_SHARE_2)
        });

        // Sensor rejects the confirm derived from a wrong secret -> non-OK command result.
        EnumCommandResult result = EnumCommandResult.OK;
        for (AbstractAttribute request : spakeManager.getConfirmRequestAttributes())
        {
            result = bleConnection.setAttribute(request);
        }
        assertEquals(EnumCommandResult.WRONG_CODE, result);
    }

    // --- helpers --------------------------------------------------------------------------

    /**
     * The full application scheme, identical for sealed access and onboarding: check the seal
     * state, take the sensorId from the advertised name, run the SPAKE handshake over the BLE
     * connection, install the negotiated key. Only {@code secret} and {@code expectedSealed} vary.
     */
    private byte[] runApplicationScheme(MockedBleConnection bleConnection, boolean expectedSealed, byte[] secret)
        throws GeneralSecurityException
    {
        CryptoManager cryptoManager = new CryptoManager();
        cryptoManager.initialize();

        SensorSealed sensorSealed = bleConnection.getAttribute(MovisensCharacteristics.SENSOR_SEALED);
        assertEquals(expectedSealed, sensorSealed.getValue());

        // sensorId = serial number, taken from the BLE advertised name (agreed convention).
        byte[] sensorId = bleConnection.getSensorSerial();
        SpakeManager spakeManager = new SpakeManager(sensorId, CLIENT_ID, secret, rng);

        // Round 1: write client share, read sensor share.
        for (AbstractAttribute request : spakeManager.getShareRequestAttributes())
        {
            bleConnection.setAttribute(request);
        }
        spakeManager.setSensorShareResponse(new AbstractReadAttribute[] {
            bleConnection.getAttribute(MovisensCharacteristics.PAKE_SENSOR_SHARE_1),
            bleConnection.getAttribute(MovisensCharacteristics.PAKE_SENSOR_SHARE_2)
        });

        // Round 2: write client confirm (sensor verifies it), read sensor confirm.
        for (AbstractAttribute request : spakeManager.getConfirmRequestAttributes())
        {
            assertEquals(EnumCommandResult.OK, bleConnection.setAttribute(request));
        }
        byte[] aesKey = spakeManager.getAesKey(new AbstractReadAttribute[] {
            bleConnection.getAttribute(MovisensCharacteristics.PAKE_SENSOR_CONFIRM_1),
            bleConnection.getAttribute(MovisensCharacteristics.PAKE_SENSOR_CONFIRM_2)
        });
        cryptoManager.setKey(aesKey); // only reached on a verified (MITM-checked) confirm
        return aesKey;
    }

    private MockedBleConnection sealedConnectionWith(byte[] secret) throws GeneralSecurityException
    {
        return connectionWith(secret, true);
    }

    private MockedBleConnection unsealedConnectionWith(byte[] secret) throws GeneralSecurityException
    {
        return connectionWith(secret, false);
    }

    private MockedBleConnection connectionWith(byte[] secret, boolean sealed) throws GeneralSecurityException
    {
        byte[] serial = MockedBleConnection.serialFrom(ADVERTISED_NAME);
        MockedSpakeSensor sensor = new MockedSpakeSensor(secret, serial, CLIENT_ID, rng);
        return new MockedBleConnection(sensor, sealed, ADVERTISED_NAME);
    }

    /**
     * Minimal application-side BLE transport over {@link MockedSpakeSensor}: routes the typed
     * GATT attributes to the mock's {@code write}/{@code read}, and exposes the sensor serial
     * the way an app derives it from the advertised name. Not platform specific.
     */
    private static final class MockedBleConnection
    {
        private final MockedSpakeSensor sensor;
        private final boolean sealed;
        private final String advertisedName;

        private byte[] pendingClientShare1;
        private byte[] pendingClientConfirm1;

        MockedBleConnection(MockedSpakeSensor sensor, boolean sealed, String advertisedName)
        {
            this.sensor = sensor;
            this.sealed = sealed;
            this.advertisedName = advertisedName;
        }

        /** sensorId = the serial number bytes parsed from the advertised name (last token). */
        byte[] getSensorSerial()
        {
            return serialFrom(advertisedName);
        }

        static byte[] serialFrom(String name)
        {
            String serial = name.substring(name.lastIndexOf(' ') + 1);
            return serial.getBytes(StandardCharsets.US_ASCII);
        }

        EnumCommandResult setAttribute(AbstractAttribute attribute) throws GeneralSecurityException
        {
            Characteristic<?> characteristic = attribute.getCharacteristic();
            byte[] raw = attribute.getRawData();

            if (characteristic == MovisensCharacteristics.PAKE_CLIENT_SHARE_1)
            {
                pendingClientShare1 = raw;
                return EnumCommandResult.OK;
            }
            if (characteristic == MovisensCharacteristics.PAKE_CLIENT_SHARE_2)
            {
                sensor.write(Attr.CLIENT_SHARE, concat(pendingClientShare1, raw));
                return EnumCommandResult.OK;
            }
            if (characteristic == MovisensCharacteristics.PAKE_CLIENT_CONFIRM_1)
            {
                pendingClientConfirm1 = raw;
                return EnumCommandResult.OK;
            }
            if (characteristic == MovisensCharacteristics.PAKE_CLIENT_CONFIRM_2)
            {
                try
                {
                    sensor.write(Attr.CLIENT_CONFIRM, concat(pendingClientConfirm1, raw));
                }
                catch (PakeException rejected)
                {
                    return EnumCommandResult.WRONG_CODE;
                }
                return EnumCommandResult.OK;
            }
            throw new IllegalArgumentException("unexpected write to " + characteristic.getName());
        }

        <T extends AbstractAttribute> T getAttribute(Characteristic<T> characteristic) throws GeneralSecurityException
        {
            byte[] data;
            if (characteristic == MovisensCharacteristics.SENSOR_SEALED)
            {
                data = new byte[] { (byte) (sealed ? 1 : 0) };
            }
            else if (characteristic == MovisensCharacteristics.PAKE_SENSOR_SHARE_1)
            {
                data = firstPart(sensor.read(Attr.SENSOR_SHARE));
            }
            else if (characteristic == MovisensCharacteristics.PAKE_SENSOR_SHARE_2)
            {
                data = secondPart(sensor.read(Attr.SENSOR_SHARE));
            }
            else if (characteristic == MovisensCharacteristics.PAKE_SENSOR_CONFIRM_1)
            {
                data = firstPart(sensor.read(Attr.SENSOR_CONFIRM));
            }
            else if (characteristic == MovisensCharacteristics.PAKE_SENSOR_CONFIRM_2)
            {
                data = secondPart(sensor.read(Attr.SENSOR_CONFIRM));
            }
            else
            {
                throw new IllegalArgumentException("unexpected read of " + characteristic.getName());
            }
            return create(characteristic, data);
        }

        byte[] sensorSessionKey()
        {
            return sensor.sessionKey();
        }

        private static final int PART1_LEN = 20; // matches SpakePairingClient _1/_2 split

        private static byte[] firstPart(byte[] value)
        {
            return Arrays.copyOfRange(value, 0, PART1_LEN);
        }

        private static byte[] secondPart(byte[] value)
        {
            return Arrays.copyOfRange(value, PART1_LEN, value.length);
        }

        @SuppressWarnings("unchecked") // createAttribute builds the characteristic's own attribute type
        private static <T extends AbstractAttribute> T create(Characteristic<T> characteristic, byte[] data)
        {
            return (T) characteristic.createAttribute(data);
        }

        private static byte[] concat(byte[] a, byte[] b)
        {
            byte[] out = Arrays.copyOf(a, a.length + b.length);
            System.arraycopy(b, 0, out, a.length, b.length);
            return out;
        }
    }
}
