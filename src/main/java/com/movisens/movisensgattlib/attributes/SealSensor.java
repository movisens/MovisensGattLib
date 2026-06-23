package com.movisens.movisensgattlib.attributes;

import java.security.GeneralSecurityException;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractWriteAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;
import com.movisens.smartgattlib.helper.PlainTextAttribute;
import com.movisens.smartgattlib.security.CryptoManager;
import com.movisens.smartgattlib.security.KeyGenerator;

public class SealSensor extends AbstractWriteAttribute
{

    public static final Characteristic<SealSensor> CHARACTERISTIC = MovisensCharacteristics.SEAL_SENSOR;

    private long key;

    public long getKey()
    {
        return key;
    }

    /**
     * @param cryptoManager active BLE crypto context; encryption must already be enabled
     * @param password      the sealing password
     * @param serial        the sensor serial number; the PBKDF2 salt, so the same
     *                      {@code (password, serial)} yields the same key on USB and BLE
     */
    public SealSensor(CryptoManager cryptoManager, String password, String serial)
    {
        if (cryptoManager.encryptionEnabled())
        {
            try
            {
                this.key = KeyGenerator.createKey(password, serial);
            }
            catch (GeneralSecurityException e)
            {
                throw new RuntimeException("failed to derive sealing key", e);
            }

            GattByteBuffer bb = GattByteBuffer.allocate(8);
            bb.putInt64(key);
            this.data = bb.array();
        }
        else
        {
            throw new RuntimeException("login needs encrypted connection");
        }
    }

    @Override
    public Characteristic<SealSensor> getCharacteristic()
    {
        return CHARACTERISTIC;
    }

    @Override
    public String toString()
    {
        return Long.toString(getKey());
    }
}
