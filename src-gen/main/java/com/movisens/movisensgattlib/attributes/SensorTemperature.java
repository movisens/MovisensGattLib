package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class SensorTemperature extends AbstractReadAttribute
{

	public static final Characteristic CHARACTERISTIC = MovisensCharacteristics.SENSOR_TEMPERATURE;
	
	private Integer temperature;
	
	public Integer getTemperature()
	{
		return temperature;
	}
	
	public String getTemperatureUnit()
	{
		return "";
	}
	
	public SensorTemperature(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		temperature = bb.getUint16();
	}

	@Override
	public Characteristic getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		return "Sensor Temperature: " + "temperature = " + getTemperature() + " " + getTemperatureUnit();
	}
}
