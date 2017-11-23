package com.movisens.movisensgattlib.attributes;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.movisensgattlib.helper.BufferedAttribute;
import com.movisens.smartgattlib.helper.AbstractAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class SkinTemperatureBuffered extends AbstractAttribute implements BufferedAttribute
{

	public static final Characteristic CHARACTERISTIC = MovisensCharacteristics.SKIN_TEMPERATURE_BUFFERED;
	
	public static final int periodLength = 60;
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

	@Override
	public String[] getValueNames()
	{
		String[] names = {"temperature"};
		return names;
	}

	@Override
	public String[] getValueUnits()
	{
		String[] names = {"Grad Celsius"};
		return names;
	}
	
	@Override
	public double[][] getValues()
	{
		int numSamples = temperature.length;
		double[][] data = new double[numSamples][1];
		
		for(int i=0; i<numSamples; i++)
		{
			data[i][0] = temperature[i];
		}
		
		return data;
	}

	public Double[] getTemperature()
	{
		return temperature;
	}
	
	public String getTemperatureUnit()
	{
		return "Grad Celsius";
	}
	
	public SkinTemperatureBuffered(byte[] data)
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
			result += "time = " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date((time + (periodLength * i)) * 1000)) + ", " + getTemperature()[i].toString() + getTemperatureUnit() + " \r\n";
		}
		return result;
	}
}
