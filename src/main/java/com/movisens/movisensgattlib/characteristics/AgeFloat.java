package com.movisens.movisensgattlib.characteristics;

import com.movisens.movisensgattlib.characteristics.base.AbstractCharacteristic;
import com.movisens.smartgattlib.GattByteBuffer;

public class AgeFloat extends AbstractCharacteristic<Float> {

    public AgeFloat(byte[] bytes) {
        super(bytes);
    }

    /**
     * @param age in years (for example 35,5)
     */
    public AgeFloat(Float age) {
        super(age);
    }

    @Override
    protected Float getValueForBytes(byte[] bytes) {
        return GattByteBuffer.wrap(bytes).getFloat32();
    }

    @Override
    protected byte[] getBytesForValue(Float value) {
        return GattByteBuffer.allocate(4).putFloat32(value).array();
    }

}
