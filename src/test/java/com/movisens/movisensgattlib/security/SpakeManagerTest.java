package com.movisens.movisensgattlib.security;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;

import org.junit.Test;

import com.movisens.movisensgattlib.security.MockedSpakeSensor.Attr;
import com.movisens.smartgattlib.helper.AbstractAttribute;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;

/**
 * Drives the {@link SpakeManager} facade through the full two-round-trip handshake against
 * {@link MockedSpakeSensor}, exercising the
 * {@link AbstractAttribute}{@code []} / {@link AbstractReadAttribute}{@code []} interface a
 * caller actually uses. Identities follow the agreed convention: {@code sensorId} = serial
 * number bytes, {@code clientId} = the constant {@code "client"}.
 */
public class SpakeManagerTest
{
    private static final byte[] SENSOR_ID = "1234567890".getBytes(StandardCharsets.US_ASCII); // serial number
    private static final byte[] CLIENT_ID = SpakeIdentities.clientId();
    private static final byte[] SEALING_SECRET = SealingPassword.toSecret("Tr0ub4dor&3");

    private static final int PART1_LEN = 20; // matches SpakePairingClient split

    private final SecureRandom rng = new SecureRandom();

    @Test
    public void derivesSessionKeyThroughAttributeFacade() throws Exception
    {
        SpakeManager manager = new SpakeManager(SENSOR_ID, CLIENT_ID, SEALING_SECRET, rng);
        MockedSpakeSensor sensor = new MockedSpakeSensor(SEALING_SECRET, SENSOR_ID, CLIENT_ID, rng);

        // Round 1: write the client share, read back the sensor share.
        sensor.write(Attr.CLIENT_SHARE, concatRequest(manager.getShareRequestAttributes()));
        manager.setSensorShareResponse(asResponse(sensor.read(Attr.SENSOR_SHARE)));

        // Round 2: write the client confirm, read back the sensor confirm -> session key.
        sensor.write(Attr.CLIENT_CONFIRM, concatRequest(manager.getConfirmRequestAttributes()));
        byte[] aesKey = manager.getAesKey(asResponse(sensor.read(Attr.SENSOR_CONFIRM)));

        assertEquals(16, aesKey.length);
        assertArrayEquals(sensor.sessionKey(), aesKey);
    }

    @Test
    public void wrongPasswordIsRejectedAndKeyIsWithheld() throws Exception
    {
        SpakeManager manager = new SpakeManager(SENSOR_ID, CLIENT_ID, SEALING_SECRET, rng);
        MockedSpakeSensor sensor = new MockedSpakeSensor(
            SealingPassword.toSecret("wrong password"), SENSOR_ID, CLIENT_ID, rng);

        sensor.write(Attr.CLIENT_SHARE, concatRequest(manager.getShareRequestAttributes()));
        manager.setSensorShareResponse(asResponse(sensor.read(Attr.SENSOR_SHARE)));

        try
        {
            sensor.write(Attr.CLIENT_CONFIRM, concatRequest(manager.getConfirmRequestAttributes()));
            fail("sensor must reject the client confirm for a wrong password");
        }
        catch (PakeException expected)
        {
            assertEquals("KEY_CONFIRMATION_FAILED", expected.getMessage());
        }
    }

    @Test
    public void tamperedSensorConfirmIsRejected() throws Exception
    {
        SpakeManager manager = new SpakeManager(SENSOR_ID, CLIENT_ID, SEALING_SECRET, rng);
        MockedSpakeSensor sensor = new MockedSpakeSensor(SEALING_SECRET, SENSOR_ID, CLIENT_ID, rng);

        sensor.write(Attr.CLIENT_SHARE, concatRequest(manager.getShareRequestAttributes()));
        manager.setSensorShareResponse(asResponse(sensor.read(Attr.SENSOR_SHARE)));
        sensor.write(Attr.CLIENT_CONFIRM, concatRequest(manager.getConfirmRequestAttributes()));

        byte[] sensorConfirm = sensor.read(Attr.SENSOR_CONFIRM);
        sensorConfirm[0] ^= 0x01; // flip a bit -> MITM / corrupted confirm

        try
        {
            manager.getAesKey(asResponse(sensorConfirm));
            fail("getAesKey must reject a confirm that does not verify");
        }
        catch (PakeException expected)
        {
            assertEquals("KEY_CONFIRMATION_FAILED", expected.getMessage());
        }
    }

    /** Concatenates the two outgoing request parts into the single value the wire carries. */
    private static byte[] concatRequest(AbstractAttribute[] parts)
    {
        return concat(parts[0].getRawData(), parts[1].getRawData());
    }

    /** Splits a read-back value into the two {@code _1}/{@code _2} response attributes (20 + rest). */
    private static AbstractReadAttribute[] asResponse(byte[] value)
    {
        return new AbstractReadAttribute[] {
            new RawReadAttribute(Arrays.copyOfRange(value, 0, PART1_LEN)),
            new RawReadAttribute(Arrays.copyOfRange(value, PART1_LEN, value.length))
        };
    }

    private static byte[] concat(byte[] a, byte[] b)
    {
        byte[] out = Arrays.copyOf(a, a.length + b.length);
        System.arraycopy(b, 0, out, a.length, b.length);
        return out;
    }

    private static final class RawReadAttribute extends AbstractReadAttribute
    {
        private RawReadAttribute(byte[] rawData)
        {
            this.data = rawData;
        }

        @Override
        public Characteristic<? extends AbstractAttribute> getCharacteristic()
        {
            return null;
        }
    }
}
