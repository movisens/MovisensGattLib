package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractWriteAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.PlainTextAttribute;

public class KeyExchangeRequest2 extends AbstractWriteAttribute implements PlainTextAttribute
{

    public static final Characteristic<KeyExchangeRequest2> CHARACTERISTIC = MovisensCharacteristics.KEY_EXCHANGE_REQUEST_2;

    public KeyExchangeRequest2(byte[] data)
    {
        this.data = data;
    }

    @Override
    public Characteristic<KeyExchangeRequest2> getCharacteristic()
    {
        return CHARACTERISTIC;
    }

}
