package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractWriteAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.PlainTextAttribute;

/**
 * Second part (bytes 20..31) of the client key-confirmation MAC (HMAC-SHA256).
 */
public class PakeClientConfirm2 extends AbstractWriteAttribute implements PlainTextAttribute
{
    public static final Characteristic<PakeClientConfirm2> CHARACTERISTIC = MovisensCharacteristics.PAKE_CLIENT_CONFIRM_2;

    public PakeClientConfirm2(byte[] data)
    {
        this.data = data;
    }

    @Override
    public Characteristic<PakeClientConfirm2> getCharacteristic()
    {
        return CHARACTERISTIC;
    }
}
