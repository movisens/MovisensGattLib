package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensdevgattlib.security.CryptoManagerProvider;
import com.movisens.movisensdevgattlib.security.KeyGenerator;
import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractWriteAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class SealSensor extends AbstractWriteAttribute
{

    public static final Characteristic<SealSensor> CHARACTERISTIC = MovisensCharacteristics.SEAL_SENSOR;

    private long[] key;

    public long[] getKey()
    {
        return key;
    }

    public SealSensor(String password)
    {
        this.key = KeyGenerator.createKey(password);
        GattByteBuffer bb = GattByteBuffer.allocate(16);
        bb.putInt64(key[0]);
        bb.putInt64(key[1]);
        this.data = bb.array();

        /* after this command the sensor is protected and attributes are encrypted */
        CryptoManagerProvider.get().setPassword(password);
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
