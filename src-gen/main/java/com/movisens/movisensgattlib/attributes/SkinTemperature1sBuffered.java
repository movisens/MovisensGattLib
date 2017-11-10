package com.movisens.movisensgattlib.attributes;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.movisensgattlib.helper.BufferedAttribute;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class SkinTemperature1sBuffered extends AbstractReadAttribute implements BufferedAttribute
{

	public static final Characteristic CHARACTERISTIC = MovisensCharacteristics.SKIN_TEMPERATURE_1S_BUFFERED;
	
	public static final int periodLength = 1;
	private long time;
	private Double temperature[];
	
	@Override
	public Date getTime()
	{
		return new Date(time*1000);
	}
	
	@Override
	public double getSamplerate()
	{
		return 1.0/periodLength;
	}
	
	public Double[] getTemperature()
	{
		return temperature;
	}
	
	public String getTemperatureUnit()
	{
		return "Grad Celsius";
	}
	
	public SkinTemperature1sBuffered(byte[] data)
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
			result += "Skin Temperature 1s Buffered: " + "time = " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date((time + (periodLength * i)) * 1000)) + ", " + "temperature = " + getTemperature()[i] + getTemperatureUnit() + "\r\n";
		}
		return result;
	}
}
