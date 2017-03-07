package com.movisens.movisensgattlib.characteristics;

import com.movisens.movisensgattlib.characteristics.base.AbstractReadOnlyCharacteristic;
import com.movisens.smartgattlib.GattByteBuffer;

public class DataAvailable extends AbstractReadOnlyCharacteristic<Boolean> {

    public DataAvailable(byte[] bytes) {
        super(bytes);
    }

    @Override
    protected Boolean getValueForBytes(byte[] bytes) {
        return GattByteBuffer.wrap(bytes).getUint8() > 0;
    }
}
