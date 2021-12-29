package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractWriteAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;
import com.movisens.smartgattlib.helper.PlainTextAttribute;

public class DisableEncryption extends AbstractWriteAttribute implements PlainTextAttribute
{

    public static final Characteristic<DisableEncryption> CHARACTERISTIC = MovisensCharacteristics.DISABLE_ENCRYPTION;

    public DisableEncryption()
    {
        GattByteBuffer bb = GattByteBuffer.allocate(1);
        bb.putInt8((byte) 0);
        this.data = bb.array();
    }

    @Override
    public Characteristic<DisableEncryption> getCharacteristic()
    {
        return CHARACTERISTIC;
    }

    @Override
    public String toString()
    {
        return "DISABLE_ENCRYPTION";
    }

}
