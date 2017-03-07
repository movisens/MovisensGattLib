package com.movisens.movisensgattlib.characteristics;

import com.movisens.movisensgattlib.characteristics.base.AbstractReadOnlyCharacteristic;
import com.movisens.smartgattlib.GattByteBuffer;

public class HrvIsValid extends AbstractReadOnlyCharacteristic<Boolean> {
    public HrvIsValid(byte[] bytes) {
        super(bytes);
    }

    @Override
    protected Boolean getValueForBytes(byte[] bytes) {
        return GattByteBuffer.wrap(bytes).getUint8() > 0;
    }
}
