package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class SkinTemperature extends AbstractReadAttribute
{

	public static final Characteristic CHARACTERISTIC = MovisensCharacteristics.SKIN_TEMPERATURE;
	
	private Double temperature;
	
	public Double getTemperature()
	{
		return temperature;
	}
	
	public String getTemperatureUnit()
	{
		return "Grad Celsius";
	}
	
	public SkinTemperature(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		temperature = ((double)bb.getInt16()) * 0.01;
	}

	@Override
	public Characteristic getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		return "Skin Temperature: " + "temperature = " + getTemperature() + " " + getTemperatureUnit();
	}
}
