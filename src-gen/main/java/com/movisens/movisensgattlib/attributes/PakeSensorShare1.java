package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;
import com.movisens.smartgattlib.helper.PlainTextAttribute;

/**
 * First part of the sensor SPAKE2 share pB = y*G + w*N (SEC1 compressed, bytes 0..19).
 */
public class PakeSensorShare1 extends AbstractReadAttribute implements PlainTextAttribute
{

	public static final Characteristic<PakeSensorShare1> CHARACTERISTIC = MovisensCharacteristics.PAKE_SENSOR_SHARE_1;
	
	
	public PakeSensorShare1(byte[] data)
	{
		if (data.length != 20)
		{
			throw new IllegalArgumentException("PakeSensorShare1 expects 20 bytes but got " + data.length);
		}
		this.data = data;
	}

	@Override
	public Characteristic<PakeSensorShare1> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

}
