package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractWriteAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.PlainTextAttribute;

public class KeyExchangeRequest1 extends AbstractWriteAttribute implements PlainTextAttribute
{

    public static final Characteristic<KeyExchangeRequest1> CHARACTERISTIC = MovisensCharacteristics.KEY_EXCHANGE_REQUEST_1;

    public KeyExchangeRequest1(byte[] data)
    {
        this.data = data;
    }

    @Override
    public Characteristic<KeyExchangeRequest1> getCharacteristic()
    {
        return CHARACTERISTIC;
    }

}
