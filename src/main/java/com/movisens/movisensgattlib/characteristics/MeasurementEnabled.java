package com.movisens.movisensgattlib.characteristics;

import com.movisens.smartgattlib.GattByteBuffer;
import com.movisens.smartgattlib.characteristics.definition.AbstractReadOnlyCharacteristic;

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
