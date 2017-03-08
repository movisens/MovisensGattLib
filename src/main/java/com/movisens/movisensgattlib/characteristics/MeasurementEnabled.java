package com.movisens.movisensgattlib.characteristics;

import com.movisens.movisensgattlib.characteristics.base.AbstractReadOnlyCharacteristic;
import com.movisens.smartgattlib.GattByteBuffer;

public class MeasurementEnabled extends AbstractReadOnlyCharacteristic<Boolean> {
    public MeasurementEnabled(byte[] value) {
        super(value);
    }

    public Boolean getValue() {
        return value;
    }

    @Override
    protected Boolean getValueForBytes(byte[] bytes) {
        return GattByteBuffer.wrap(bytes).getUint8() > 0;
    }
}
