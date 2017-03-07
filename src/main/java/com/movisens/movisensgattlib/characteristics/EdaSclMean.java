package com.movisens.movisensgattlib.characteristics;

import com.movisens.smartgattlib.GattByteBuffer;

/**
 * Created by Robert Zetzsche on 07.03.2017.
 */

public class EdaSclMean {
    Double value = Double.NaN;

    public EdaSclMean(byte[] value) {
        this.value = GattByteBuffer.wrap(value).getUint16().doubleValue();
        if (this.value == -1)
            this.value = Double.NaN;
    }

    public Double getValue() {
        return value;
    }

    public boolean isValid() {
        return !value.isNaN();
    }
}
