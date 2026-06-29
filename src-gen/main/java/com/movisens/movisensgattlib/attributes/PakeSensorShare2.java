package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;
import com.movisens.smartgattlib.helper.PlainTextAttribute;

/**
 * Second part of the sensor SPAKE2 share pB (SEC1 compressed, bytes 20..32).
 */
public class PakeSensorShare2 extends AbstractReadAttribute implements PlainTextAttribute
{

	public static final Characteristic<PakeSensorShare2> CHARACTERISTIC = MovisensCharacteristics.PAKE_SENSOR_SHARE_2;
	
	
	public PakeSensorShare2(byte[] data)
	{
		if (data.length != 13)
		{
			throw new IllegalArgumentException("PakeSensorShare2 expects 13 bytes but got " + data.length);
		}
		this.data = data;
	}

	@Override
	public Characteristic<PakeSensorShare2> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

}
