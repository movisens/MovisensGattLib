package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractWriteAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.PlainTextAttribute;

/**
 * First part (bytes 0..19) of the client balanced-SPAKE2 share pA, SEC1 compressed.
 * Plaintext bootstrap attribute; the session is encrypted only after key confirmation.
 */
public class PakeClientShare1 extends AbstractWriteAttribute implements PlainTextAttribute
{
    public static final Characteristic<PakeClientShare1> CHARACTERISTIC = MovisensCharacteristics.PAKE_CLIENT_SHARE_1;

    public PakeClientShare1(byte[] data)
    {
        this.data = data;
    }

    @Override
    public Characteristic<PakeClientShare1> getCharacteristic()
    {
        return CHARACTERISTIC;
    }
}
