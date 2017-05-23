package com.movisens.movisensgattlib.characteristics;

import com.movisens.smartgattlib.GattByteBuffer;
import com.movisens.smartgattlib.characteristics.definition.AbstractReadOnlyCharacteristic;

public class Rmssd extends AbstractReadOnlyCharacteristic<Double> {
    Double value = Double.NaN;

    public Rmssd(byte[] value) {
        super(value);
        if (this.value == -1) {
            this.value = Double.NaN;
        }
    }

    @Override
    public boolean isValid() {
        return !value.isNaN();
    }

    @Override
    protected Double getValueForBytes(byte[] bytes) {
        return GattByteBuffer.wrap(bytes).getInt16().doubleValue();
    }
}
