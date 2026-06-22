package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.PlainTextAttribute;

/**
 * Second part (bytes 20..32) of the sensor balanced-SPAKE2 share pB, SEC1 compressed.
 */
public class PakeSensorShare2 extends AbstractReadAttribute implements PlainTextAttribute
{
    public static final Characteristic<PakeSensorShare2> CHARACTERISTIC = MovisensCharacteristics.PAKE_SENSOR_SHARE_2;

    public PakeSensorShare2(byte[] data)
    {
        this.data = data;
    }

    @Override
    public Characteristic<PakeSensorShare2> getCharacteristic()
    {
        return CHARACTERISTIC;
    }
}
