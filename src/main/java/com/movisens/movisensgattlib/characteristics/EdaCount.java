package com.movisens.movisensgattlib.characteristics;

import com.movisens.movisensgattlib.characteristics.base.AbstractReadOnlyCharacteristic;
import com.movisens.smartgattlib.GattByteBuffer;

/**
 * Created by Robert Zetzsche on 07.03.2017.
 */

public class EdaCount extends AbstractReadOnlyCharacteristic<Integer> {

    public EdaCount(byte[] bytes) {
        super(bytes);
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
