package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;

public class AuthConfirm extends AbstractReadAttribute
{

    public static final Characteristic<AuthConfirm> CHARACTERISTIC = MovisensCharacteristics.AUTH_CONFIRM;

    public AuthConfirm(byte[] data)
    {
        this.data = data;
    }

    @Override
    public Characteristic<AuthConfirm> getCharacteristic()
    {
        return CHARACTERISTIC;
    }
}
