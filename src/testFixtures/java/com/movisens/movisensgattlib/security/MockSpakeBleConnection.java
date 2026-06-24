package com.movisens.movisensgattlib.security;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Arrays;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.movisensgattlib.attributes.EnumCommandResult;
import com.movisens.movisensgattlib.attributes.PakeStart;
import com.movisens.movisensgattlib.attributes.SealSensor;
import com.movisens.movisensgattlib.attributes.UnsealSensor;
import com.movisens.smartgattlib.helper.AbstractAttribute;
import com.movisens.smartgattlib.helper.Characteristic;

/**
 * Reusable {@link SpakeGattConnection} over a {@link SpakeSensorEmulator}: maps the GATT
 * attributes onto the emulated sensor exactly the way the firmware will expose them.
 *
 * <ul>
 *   <li>writes {@code pake_client_share_1/2} and {@code pake_client_confirm_1/2}
 *       (reassembled from the two parts);</li>
 *   <li>reads {@code pake_sensor_share_1/2} and {@code pake_sensor_confirm_1/2}
 *       (split 20 bytes / rest), plus {@code sensor_sealed} and {@code command_result};</li>
 *   <li>protected writes ({@code seal_sensor}/{@code unseal_sensor}) before an authenticated
 *       handshake return {@link EnumCommandResult#ACCESS_DENIED}.</li>
 * </ul>
 *
 * <p>This is the wire-level counterpart used to drive consumers (via {@link SpakeSession}) without
 * a real sensor; it is modelled on the original {@code SpakeManagerBleSchemeTest.MockedBleConnection}.</p>
 */
public final class MockSpakeBleConnection implements SpakeGattConnection
{
    private static final int PART1_LEN = 20; // matches the SpakePairingClient _1/_2 split

    private final SpakeSensorEmulator emulator;
    private final String advertisedName;

    private byte[] pendingClientShare1;
    private byte[] pendingClientConfirm1;
    private EnumCommandResult lastResult = EnumCommandResult.OK;

    public MockSpakeBleConnection(SpakeSensorEmulator emulator, String advertisedName)
    {
        this.emulator = emulator;
        this.advertisedName = advertisedName;
    }

    /** sensorId = the serial number bytes parsed from the advertised name (last token). */
    public byte[] getSensorSerial()
    {
        return serialFrom(advertisedName);
    }

    public static byte[] serialFrom(String name)
    {
        String serial = name.substring(name.lastIndexOf(' ') + 1);
        return serial.getBytes(StandardCharsets.US_ASCII);
    }

    public byte[] sensorSessionKey()
    {
        return emulator.sessionKey();
    }

    @Override
    public EnumCommandResult setAttribute(AbstractAttribute attribute) throws GeneralSecurityException
    {
        Characteristic<?> characteristic = attribute.getCharacteristic();
        byte[] raw = attribute.getRawData();

        if (characteristic == MovisensCharacteristics.PAKE_START || attribute instanceof PakeStart)
        {
            return record(emulator.startPairing());
        }
        if (characteristic == MovisensCharacteristics.PAKE_CLIENT_SHARE_1)
        {
            pendingClientShare1 = raw;
            return ok();
        }
        if (characteristic == MovisensCharacteristics.PAKE_CLIENT_SHARE_2)
        {
            return record(emulator.onClientShare(concat(pendingClientShare1, raw)));
        }
        if (characteristic == MovisensCharacteristics.PAKE_CLIENT_CONFIRM_1)
        {
            pendingClientConfirm1 = raw;
            return ok();
        }
        if (characteristic == MovisensCharacteristics.PAKE_CLIENT_CONFIRM_2)
        {
            return record(emulator.onClientConfirm(concat(pendingClientConfirm1, raw)));
        }
        if (characteristic == MovisensCharacteristics.SEAL_SENSOR || attribute instanceof SealSensor)
        {
            if (!emulator.isSessionAuthenticated())
            {
                return record(EnumCommandResult.ACCESS_DENIED);
            }
            emulator.seal();
            return ok();
        }
        if (characteristic == MovisensCharacteristics.UNSEAL_SENSOR || attribute instanceof UnsealSensor)
        {
            if (!emulator.isSessionAuthenticated())
            {
                return record(EnumCommandResult.ACCESS_DENIED);
            }
            emulator.unseal();
            return ok();
        }
        // Any other attribute is a protected write: allowed only after an authenticated handshake.
        return record(emulator.isSessionAuthenticated() ? EnumCommandResult.OK : EnumCommandResult.ACCESS_DENIED);
    }

    @Override
    public <T extends AbstractAttribute> T getAttribute(Characteristic<T> characteristic) throws GeneralSecurityException
    {
        byte[] data;
        if (characteristic == MovisensCharacteristics.SENSOR_SEALED)
        {
            data = new byte[] { (byte) (emulator.isSealed() ? 1 : 0) };
        }
        else if (characteristic == MovisensCharacteristics.COMMAND_RESULT)
        {
            data = new byte[] { (byte) lastResult.getValue() };
        }
        else if (characteristic == MovisensCharacteristics.PAKE_SENSOR_SHARE_1)
        {
            data = firstPart(emulator.sensorShare());
        }
        else if (characteristic == MovisensCharacteristics.PAKE_SENSOR_SHARE_2)
        {
            data = secondPart(emulator.sensorShare());
        }
        else if (characteristic == MovisensCharacteristics.PAKE_SENSOR_CONFIRM_1)
        {
            data = firstPart(emulator.sensorConfirm());
        }
        else if (characteristic == MovisensCharacteristics.PAKE_SENSOR_CONFIRM_2)
        {
            data = secondPart(emulator.sensorConfirm());
        }
        else
        {
            throw new IllegalArgumentException("unexpected read of " + characteristic.getName());
        }
        return create(characteristic, data);
    }

    private EnumCommandResult ok()
    {
        return record(EnumCommandResult.OK);
    }

    private EnumCommandResult record(EnumCommandResult result)
    {
        this.lastResult = result;
        return result;
    }

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
