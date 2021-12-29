package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.PlainTextAttribute;

public class KeyExchangeResponse2 extends AbstractReadAttribute implements PlainTextAttribute
{

    public static final Characteristic<KeyExchangeResponse2> CHARACTERISTIC = MovisensCharacteristics.KEY_EXCHANGE_RESPONSE_2;

    public KeyExchangeResponse2(byte[] data)
    {
        this.data = data;
    }

    @Override
    public Characteristic<KeyExchangeResponse2> getCharacteristic()
    {
        return CHARACTERISTIC;
    }

}
