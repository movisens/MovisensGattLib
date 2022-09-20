package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class SensorTemperature extends AbstractReadAttribute
{

	public static final Characteristic<SensorTemperature> CHARACTERISTIC = MovisensCharacteristics.SENSOR_TEMPERATURE;
	
	private Double temperature;
	
	public Double getTemperature()
	{
		return temperature;
	}
	
	public String getTemperatureUnit()
	{
		return "°C";
	}
	
	public SensorTemperature(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		temperature = ((double)bb.getUint16()) * 0.1;
	}

	@Override
	public Characteristic<SensorTemperature> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		return getTemperature().toString() + getTemperatureUnit();
	}
}
