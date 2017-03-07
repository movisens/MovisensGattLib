package com.movisens.movisensgattlib.characteristics;

import com.movisens.movisensgattlib.characteristics.base.AbstractReadOnlyCharacteristic;
import com.movisens.smartgattlib.GattByteBuffer;

/**
 * Created by Robert Zetzsche on 07.03.2017.
 */

public class EdaSclMean extends AbstractReadOnlyCharacteristic<Double> {
    Double value = Double.NaN;

    public EdaSclMean(byte[] value) {
        super(value);
        if (this.value == -1)
            this.value = Double.NaN;
    }

    @Override
    public boolean isValid() {
        return !value.isNaN();
    }

    @Override
    protected Double getValueForBytes(byte[] bytes) {
        return GattByteBuffer.wrap(bytes).getUint16().doubleValue();
    }
}
