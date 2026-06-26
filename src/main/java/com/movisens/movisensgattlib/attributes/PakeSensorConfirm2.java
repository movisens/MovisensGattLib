package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.PlainTextAttribute;

/**
 * Second part of the sensor key-confirmation MAC (bytes 20..31).
 */
public class PakeSensorConfirm2 extends AbstractReadAttribute implements PlainTextAttribute
{
    public static final Characteristic<PakeSensorConfirm2> CHARACTERISTIC = MovisensCharacteristics.PAKE_SENSOR_CONFIRM_2;

    public PakeSensorConfirm2(byte[] data)
    {
        this.data = data;
    }

    @Override
    public Characteristic<PakeSensorConfirm2> getCharacteristic()
    {
        return CHARACTERISTIC;
    }
}
