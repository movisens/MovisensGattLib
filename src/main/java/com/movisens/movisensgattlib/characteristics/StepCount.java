package com.movisens.movisensgattlib.characteristics;

import com.movisens.movisensgattlib.characteristics.base.AbstractReadOnlyCharacteristic;
import com.movisens.smartgattlib.GattByteBuffer;

public class StepCount extends AbstractReadOnlyCharacteristic<Integer> {

    public StepCount(byte[] value) {
        super(value);
    }

    @Override
    public boolean isValid() {
        return value >= 0;
    }

    @Override
    protected Integer getValueForBytes(byte[] bytes) {
        return GattByteBuffer.wrap(bytes).getUint16();
    }
}
