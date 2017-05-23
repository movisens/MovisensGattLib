package com.movisens.movisensgattlib.characteristics;

import com.movisens.smartgattlib.GattByteBuffer;
import com.movisens.smartgattlib.characteristics.definition.AbstractReadOnlyCharacteristic;

public class MovementAcceleration extends AbstractReadOnlyCharacteristic<Double> {

    public MovementAcceleration(byte[] value) {
        super(value);
    }

    @Override
    protected Double getValueForBytes(byte[] bytes) {
        Double lsbValue = 1.0 / 256;
        return GattByteBuffer.wrap(bytes).getUint16().doubleValue() * lsbValue;
    }
}
