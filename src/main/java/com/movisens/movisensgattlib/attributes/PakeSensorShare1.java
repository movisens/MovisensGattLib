package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.PlainTextAttribute;

/**
 * First part (bytes 0..19) of the sensor balanced-SPAKE2 share pB, SEC1 compressed.
 */
public class PakeSensorShare1 extends AbstractReadAttribute implements PlainTextAttribute
{
    public static final Characteristic<PakeSensorShare1> CHARACTERISTIC = MovisensCharacteristics.PAKE_SENSOR_SHARE_1;

    public PakeSensorShare1(byte[] data)
    {
        this.data = data;
    }

    @Override
    public Characteristic<PakeSensorShare1> getCharacteristic()
    {
        return CHARACTERISTIC;
    }
}
