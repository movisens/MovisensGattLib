package com.movisens.movisensgattlib.characteristics;

import com.movisens.smartgattlib.GattByteBuffer;
import com.movisens.smartgattlib.characteristics.definition.AbstractReadOnlyCharacteristic;

public class HrvIsValid extends AbstractReadOnlyCharacteristic<Boolean> {
    public HrvIsValid(byte[] bytes) {
        super(bytes);
    }

    @Override
    protected Boolean getValueForBytes(byte[] bytes) {
        return GattByteBuffer.wrap(bytes).getUint8() > 0;
    }
}
