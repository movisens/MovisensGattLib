package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensdevgattlib.security.CryptoManagerProvider;
import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadWriteAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class AgeFloat extends AbstractReadWriteAttribute
{

    public static final Characteristic<AgeFloat> CHARACTERISTIC = MovisensCharacteristics.AGE_FLOAT;

    private Double age;

    public Double getAge()
    {
        return age;
    }

    public String getAgeUnit()
    {
        return "a";
    }

    public AgeFloat(Double age)
    {
        if (age < 0.0)
        {
            throw new RuntimeException("age out of range! Min value is 0.0");
        }
        if (age > 200.0)
        {
            throw new RuntimeException("age out of range! Max value is 200.0");
        }
        this.age = age;
        GattByteBuffer bb = GattByteBuffer.allocate(4);
        bb.putFloat32(age.floatValue());

        this.data = CryptoManagerProvider.get().processBeforeSend(bb.array());
    }

    public AgeFloat(byte[] inputData)
    {
        this.data = CryptoManagerProvider.get().processAfterReceive(inputData);

        GattByteBuffer bb = GattByteBuffer.wrap(data);
        age = new Double(bb.getFloat32());
    }

    @Override
    public Characteristic<AgeFloat> getCharacteristic()
    {
        return CHARACTERISTIC;
    }

    @Override
    public String toString()
    {
        return getAge().toString() + getAgeUnit();
    }
}
