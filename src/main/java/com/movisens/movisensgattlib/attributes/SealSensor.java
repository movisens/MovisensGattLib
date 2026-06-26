package com.movisens.movisensgattlib.attributes;

import java.security.GeneralSecurityException;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractWriteAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;
import com.movisens.smartgattlib.helper.PlainTextAttribute;
import com.movisens.smartgattlib.security.CryptoManager;
import com.movisens.smartgattlib.security.KeyGenerator;

/**
 * Seals the sensor and sets the given key.
 * This triggers a command_result notification.
 * Possible results:
 * - ok: the sensor was sealed.
 * - ACCESS_DENIED: the connection is not encrypted, not authenticated or not allowed
 *   to seal in the current security state.
 */
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
     */
    public SealSensor(CryptoManager cryptoManager, String password)
    {
        if (cryptoManager.encryptionEnabled())
        {
            try
            {
                this.key = KeyGenerator.createKey(password);
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
            throw new RuntimeException("sealing needs encrypted connection");
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
