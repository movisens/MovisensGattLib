package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractWriteAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;
import com.movisens.smartgattlib.security.CryptoManagerProvider;

public class UnsealSensor extends AbstractWriteAttribute
{

    public static final Characteristic<UnsealSensor> CHARACTERISTIC = MovisensCharacteristics.UNSEAL_SENSOR;

    public UnsealSensor()
    {
        GattByteBuffer bb = GattByteBuffer.allocate(1);
        bb.putInt8((byte) 0);
        this.data = bb.array();

        /* after this command the sensor is no longer protected and attributes are not encrypted */
        CryptoManagerProvider.get().disableEncryption();
    }

    @Override
    public Characteristic<UnsealSensor> getCharacteristic()
    {
        return CHARACTERISTIC;
    }

    @Override
    public String toString()
    {
        return "UNSEAL_SENSOR";
    }
}
