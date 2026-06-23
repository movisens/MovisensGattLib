package com.movisens.movisensgattlib.security;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;

import org.junit.Test;

import com.movisens.movisensgattlib.attributes.PakeSensorConfirm1;
import com.movisens.movisensgattlib.attributes.PakeSensorConfirm2;
import com.movisens.movisensgattlib.attributes.PakeSensorShare1;
import com.movisens.movisensgattlib.attributes.PakeSensorShare2;
import com.movisens.movisensgattlib.security.MockSpake2Sensor.Attr;

/**
 * Drives the production-facing {@link SpakePairingClient} through the full pairing
 * against {@link MockSpake2Sensor}. The only coupling is the GATT attribute interface.
 */
public class SpakePairingClientTest
{
    private static final byte[] SENSOR_ID = "sensor".getBytes(StandardCharsets.US_ASCII);
    private static final byte[] CLIENT_ID = "client".getBytes(StandardCharsets.US_ASCII);
    private static final byte[] CODE = {0, 1, 2, 2, 4, 3};
    private static final byte[] WRONG_CODE = {0, 1, 2, 2, 4, 4};

    private final SecureRandom rng = new SecureRandom();

    @Test
    public void pairsWithCorrectCode() throws Exception
    {
        SpakePairingClient client = new SpakePairingClient(SENSOR_ID, CLIENT_ID, CODE, rng);
        MockSpake2Sensor sensor = new MockSpake2Sensor(CODE, SENSOR_ID, CLIENT_ID, rng);

        byte[] clientShare = client.clientShare();
        sensor.write(Attr.CLIENT_SHARE, clientShare);
        byte[] sensorShare = sensor.read(Attr.SENSOR_SHARE);

        // Shares travel as SEC1 compressed points (33 bytes), not 65.
        assertEquals(33, clientShare.length);
        assertEquals(33, sensorShare.length);

        client.setSensorShare(sensorShare);
        sensor.write(Attr.CLIENT_CONFIRM, client.clientConfirm());
        client.verifySensorConfirm(sensor.read(Attr.SENSOR_CONFIRM));

        assertEquals(16, client.sessionKey().length);
        assertArrayEquals(client.sessionKey(), sensor.sessionKey());
    }

    @Test
    public void pairsViaTypedAttributeObjects() throws Exception
    {
        SpakePairingClient client = new SpakePairingClient(SENSOR_ID, CLIENT_ID, CODE, rng);
        MockSpake2Sensor sensor = new MockSpake2Sensor(CODE, SENSOR_ID, CLIENT_ID, rng);

        byte[] cs1 = client.clientShare1().getRawData();
        byte[] cs2 = client.clientShare2().getRawData();
        assertEquals(20, cs1.length);
        assertEquals(13, cs2.length); // 33-byte compressed share split 20 + 13

        sensor.write(Attr.CLIENT_SHARE, concat(cs1, cs2));
        byte[] ss = sensor.read(Attr.SENSOR_SHARE);
        client.setSensorShare(new PakeSensorShare1(Arrays.copyOfRange(ss, 0, 20)),
                              new PakeSensorShare2(Arrays.copyOfRange(ss, 20, ss.length)));

        byte[] cc1 = client.clientConfirm1().getRawData();
        byte[] cc2 = client.clientConfirm2().getRawData();
        assertEquals(20, cc1.length);
        assertEquals(12, cc2.length); // 32-byte confirm MAC split 20 + 12

        sensor.write(Attr.CLIENT_CONFIRM, concat(cc1, cc2));
        byte[] sc = sensor.read(Attr.SENSOR_CONFIRM);
        client.verifySensorConfirm(new PakeSensorConfirm1(Arrays.copyOfRange(sc, 0, 20)),
                                   new PakeSensorConfirm2(Arrays.copyOfRange(sc, 20, sc.length)));

        assertEquals(16, client.sessionKey().length);
        assertArrayEquals(client.sessionKey(), sensor.sessionKey());
    }

    private static byte[] concat(byte[] a, byte[] b)
    {
        byte[] out = Arrays.copyOf(a, a.length + b.length);
        System.arraycopy(b, 0, out, a.length, b.length);
        return out;
    }

    @Test
    public void tamperedSensorShareIsRejected() throws Exception
    {
        SpakePairingClient client = new SpakePairingClient(SENSOR_ID, CLIENT_ID, CODE, rng);
        MockSpake2Sensor sensor = new MockSpake2Sensor(CODE, SENSOR_ID, CLIENT_ID, rng);

        sensor.write(Attr.CLIENT_SHARE, client.clientShare());
        byte[] sensorShare = sensor.read(Attr.SENSOR_SHARE);
        sensorShare[0] = 0x05; // invalid SEC1 compression prefix -> deterministic reject

        try
        {
            client.setSensorShare(sensorShare);
            fail("client must reject a malformed compressed sensor share");
        }
        catch (java.security.GeneralSecurityException expected)
        {
        }
    }

    @Test
    public void sensorWithholdsConfirmBeforeClientConfirm() throws Exception
    {
        SpakePairingClient client = new SpakePairingClient(SENSOR_ID, CLIENT_ID, CODE, rng);
        MockSpake2Sensor sensor = new MockSpake2Sensor(CODE, SENSOR_ID, CLIENT_ID, rng);

        sensor.write(Attr.CLIENT_SHARE, client.clientShare());
        client.setSensorShare(sensor.read(Attr.SENSOR_SHARE));

        try
        {
            sensor.read(Attr.SENSOR_CONFIRM);
            fail("sensor must not release its confirm before the client confirm");
        }
        catch (PakeException expected)
        {
        }
    }

    @Test
    public void wrongCodeIsRejectedAndKeyIsWithheld() throws Exception
    {
        SpakePairingClient client = new SpakePairingClient(SENSOR_ID, CLIENT_ID, CODE, rng);
        MockSpake2Sensor sensor = new MockSpake2Sensor(WRONG_CODE, SENSOR_ID, CLIENT_ID, rng);

        sensor.write(Attr.CLIENT_SHARE, client.clientShare());
        client.setSensorShare(sensor.read(Attr.SENSOR_SHARE));

        // Sensor rejects the client confirm (wrong code).
        try
        {
            sensor.write(Attr.CLIENT_CONFIRM, client.clientConfirm());
            fail("sensor must reject the client confirm for a wrong code");
        }
        catch (PakeException expected)
        {
            assertEquals("KEY_CONFIRMATION_FAILED", expected.getMessage());
        }

        // Session key must not be released before a verified sensor confirm.
        try
        {
            client.sessionKey();
            fail("session key must be withheld until the sensor is verified");
        }
        catch (IllegalStateException expected)
        {
        }
    }

    @Test
    public void secondSensorShareAfterVerificationIsRejected() throws Exception
    {
        SpakePairingClient client = new SpakePairingClient(SENSOR_ID, CLIENT_ID, CODE, rng);
        MockSpake2Sensor sensor = new MockSpake2Sensor(CODE, SENSOR_ID, CLIENT_ID, rng);

        sensor.write(Attr.CLIENT_SHARE, client.clientShare());
        byte[] sensorShare = sensor.read(Attr.SENSOR_SHARE);
        client.setSensorShare(sensorShare);
        sensor.write(Attr.CLIENT_CONFIRM, client.clientConfirm());
        client.verifySensorConfirm(sensor.read(Attr.SENSOR_CONFIRM));
        byte[] verifiedKey = client.sessionKey();

        try
        {
            client.setSensorShare(sensorShare);
            fail("verified client must reject a second sensor share");
        }
        catch (PakeException expected)
        {
            assertEquals("INVALID_PAKE_STATE: sensor share already consumed", expected.getMessage());
        }

        assertArrayEquals(verifiedKey, client.sessionKey());
    }

    @Test
    public void confirmBeforeSensorShareIsRejected() throws Exception
    {
        SpakePairingClient client = new SpakePairingClient(SENSOR_ID, CLIENT_ID, CODE, rng);
        client.clientShare();

        try
        {
            client.clientConfirm();
            fail("clientConfirm() must require setSensorShare() first");
        }
        catch (PakeException expected)
        {
        }
    }
}
