package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.PlainTextAttribute;

public class KeyExchangeResponse1 extends AbstractReadAttribute implements PlainTextAttribute
{

    public static final Characteristic<KeyExchangeResponse1> CHARACTERISTIC = MovisensCharacteristics.KEY_EXCHANGE_RESPONSE_1;

    public KeyExchangeResponse1(byte[] data)
    {
        this.data = data;
    }

    @Override
    public Characteristic<KeyExchangeResponse1> getCharacteristic()
    {
        return CHARACTERISTIC;
    }

}
