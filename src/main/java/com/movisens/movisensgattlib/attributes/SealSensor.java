package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractWriteAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;
import com.movisens.smartgattlib.helper.PlainTextAttribute;
import com.movisens.smartgattlib.security.CryptoManager;
import com.movisens.smartgattlib.security.KeyGenerator;

public class SealSensor extends AbstractWriteAttribute implements PlainTextAttribute
{

    public static final Characteristic<SealSensor> CHARACTERISTIC = MovisensCharacteristics.SEAL_SENSOR;

    private long[] key;

    public long[] getKey()
    {
        return key;
    }

    public SealSensor(CryptoManager cryptoManager, String password)
    {
        if (cryptoManager.encryptionEnabled())
        {
            this.key = KeyGenerator.createKey(password);

            GattByteBuffer bb = GattByteBuffer.allocate(8);
            bb.putInt64(key[0]);
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
        return getKey().toString();
    }
}
