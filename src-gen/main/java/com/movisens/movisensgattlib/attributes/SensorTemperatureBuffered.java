package com.movisens.movisensgattlib.attributes;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class SensorTemperatureBuffered extends AbstractReadAttribute
{

	public static final Characteristic CHARACTERISTIC = MovisensCharacteristics.SENSOR_TEMPERATURE_BUFFERED;
	
	public static final int periodLength = 60;
	private long time;
	private Integer temperature[];
	
	public Integer[] getTemperature()
	{
		return temperature;
	}
	
	public String getTemperatureUnit()
	{
		return "";
	}
	
	public SensorTemperatureBuffered(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		
		time = bb.getUint32();
		short numValues = bb.getUint8();
		
		temperature = new Integer[numValues];
		
		for (int i = 0; i < numValues; i++)
		{
			temperature[i] = bb.getUint16();
		}
	}

	@Override
	public Characteristic getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		String result = "";
		for(int i=0; i<temperature.length; i++)
		{
			result += "Sensor Temperature Buffered: " + "time = " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date((time + (periodLength * i)) * 1000)) + ", " + "temperature = " + getTemperature()[i] + "\r\n";
		}
		return result;
	}
}