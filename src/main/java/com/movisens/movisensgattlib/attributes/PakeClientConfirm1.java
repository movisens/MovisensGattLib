package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractWriteAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.PlainTextAttribute;

/**
 * First part (bytes 0..19) of the client key-confirmation MAC (HMAC-SHA256).
 */
public class PakeClientConfirm1 extends AbstractWriteAttribute implements PlainTextAttribute
{
    public static final Characteristic<PakeClientConfirm1> CHARACTERISTIC = MovisensCharacteristics.PAKE_CLIENT_CONFIRM_1;

    public PakeClientConfirm1(byte[] data)
    {
        this.data = data;
    }

    @Override
    public Characteristic<PakeClientConfirm1> getCharacteristic()
    {
        return CHARACTERISTIC;
    }
}
