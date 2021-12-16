package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;
import com.movisens.smartgattlib.helper.PlainTextAttribute;

public class SensorSealed extends AbstractReadAttribute implements PlainTextAttribute
{

	public static final Characteristic<SensorSealed> CHARACTERISTIC = MovisensCharacteristics.SENSOR_SEALED;
	
	private Boolean value;
	
	public Boolean getValue()
	{
		return value;
	}
	
	public String getValueUnit()
	{
		return "";
	}
	
	public SensorSealed(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		value = bb.getBoolean();
	}

	@Override
	public Characteristic<SensorSealed> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		return getValue().toString();
	}
}
