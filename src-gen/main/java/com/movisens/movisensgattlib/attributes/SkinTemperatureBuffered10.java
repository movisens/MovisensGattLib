package com.movisens.movisensgattlib.attributes;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class SkinTemperatureBuffered10 extends AbstractReadAttribute
{

	public static final Characteristic CHARACTERISTIC = MovisensCharacteristics.SKIN_TEMPERATURE_BUFFERED__1_0;
	
	public static final int periodLength = 1;
	private long time;
	private Double temperature[];
	
	public Double[] getTemperature()
	{
		return temperature;
	}
	
	public String getTemperatureUnit()
	{
		return "Grad Celsius";
	}
	
	public SkinTemperatureBuffered10(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		
		time = bb.getUint32();
		short numValues = bb.getUint8();
		
		temperature = new Double[numValues];
		
		for (int i = 0; i < numValues; i++)
		{
			temperature[i] = ((double)bb.getInt16()) * 0.01;
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
			result += "Skin Temperature Buffered  1 0: " + "time = " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date((time + (periodLength * i)) * 1000)) + ", " + "temperature = " + getTemperature()[i] + getTemperatureUnit() + "\r\n";
		}
		return result;
	}
}
