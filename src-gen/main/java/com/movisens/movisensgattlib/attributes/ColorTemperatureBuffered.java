package com.movisens.movisensgattlib.attributes;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class ColorTemperatureBuffered extends AbstractReadAttribute
{

	public static final Characteristic CHARACTERISTIC = MovisensCharacteristics.COLOR_TEMPERATURE_BUFFERED;
	
	public static final int periodLength = 60;
	private long time;
	private Long colorTemperature[];
	
	public Long[] getColorTemperature()
	{
		return colorTemperature;
	}
	
	public String getColorTemperatureUnit()
	{
		return "";
	}
	
	public ColorTemperatureBuffered(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		
		time = bb.getUint32();
		short numValues = bb.getUint8();
		
		colorTemperature = new Long[numValues];
		
		for (int i = 0; i < numValues; i++)
		{
			colorTemperature[i] = bb.getUint32();
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
		for(int i=0; i<colorTemperature.length; i++)
		{
			result += "Color Temperature Buffered: " + "time = " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date((time + (periodLength * i)) * 1000)) + ", " + "colorTemperature = " + getColorTemperature()[i] + "\r\n";
		}
		return result;
	}
}
