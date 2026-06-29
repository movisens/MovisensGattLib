package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;
import com.movisens.smartgattlib.helper.PlainTextAttribute;

/**
 * First part of the sensor key-confirmation MAC HMAC-SHA256 (bytes 0..19).
 */
public class PakeSensorConfirm1 extends AbstractReadAttribute implements PlainTextAttribute
{

	public static final Characteristic<PakeSensorConfirm1> CHARACTERISTIC = MovisensCharacteristics.PAKE_SENSOR_CONFIRM_1;
	
	
	public PakeSensorConfirm1(byte[] data)
	{
		if (data.length != 20)
		{
			throw new IllegalArgumentException("PakeSensorConfirm1 expects 20 bytes but got " + data.length);
		}
		this.data = data;
	}

	@Override
	public Characteristic<PakeSensorConfirm1> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

}
