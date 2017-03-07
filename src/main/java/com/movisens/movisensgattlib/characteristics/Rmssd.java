package com.movisens.movisensgattlib.characteristics;

import com.movisens.movisensgattlib.characteristics.base.AbstractReadOnlyCharacteristic;
import com.movisens.smartgattlib.GattByteBuffer;

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
