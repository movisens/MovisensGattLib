package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractWriteAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.PlainTextAttribute;

/**
 * Second part (bytes 20..32) of the client balanced-SPAKE2 share pA, SEC1 compressed.
 */
public class PakeClientShare2 extends AbstractWriteAttribute implements PlainTextAttribute
{
    public static final Characteristic<PakeClientShare2> CHARACTERISTIC = MovisensCharacteristics.PAKE_CLIENT_SHARE_2;

    public PakeClientShare2(byte[] data)
    {
        this.data = data;
    }

    @Override
    public Characteristic<PakeClientShare2> getCharacteristic()
    {
        return CHARACTERISTIC;
    }
}
