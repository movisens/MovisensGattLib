package com.movisens.movisensgattlib.characteristics;

import com.movisens.smartgattlib.GattByteBuffer;

/**
 * Created by Robert Zetzsche on 07.03.2017.
 */

public class EdaCount {
    Integer value = -1;

    public EdaCount(byte[] value) {
        this.value = GattByteBuffer.wrap(value).getUint16();
    }

    public Integer getValue() {
        return value;
    }

    public boolean isValid() {
        return value >= 0;
    }
}
