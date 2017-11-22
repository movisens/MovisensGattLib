package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class ColorTemperature extends AbstractReadAttribute
{

	public static final Characteristic CHARACTERISTIC = MovisensCharacteristics.COLOR_TEMPERATURE;
	
	private Long colorTemperature;
	
	public Long getColorTemperature()
	{
		return colorTemperature;
	}
	
	public String getColorTemperatureUnit()
	{
		return "";
	}
	
	public ColorTemperature(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		colorTemperature = bb.getUint32();
	}

	@Override
	public Characteristic getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		return getColorTemperature().toString();
	}
}
