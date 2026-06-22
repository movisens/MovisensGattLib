package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.PlainTextAttribute;

/**
 * First part (bytes 0..19) of the sensor key-confirmation MAC (HMAC-SHA256).
 */
public class PakeSensorConfirm1 extends AbstractReadAttribute implements PlainTextAttribute
{
    public static final Characteristic<PakeSensorConfirm1> CHARACTERISTIC = MovisensCharacteristics.PAKE_SENSOR_CONFIRM_1;

    public PakeSensorConfirm1(byte[] data)
    {
        this.data = data;
    }

    @Override
    public Characteristic<PakeSensorConfirm1> getCharacteristic()
    {
        return CHARACTERISTIC;
    }
}
