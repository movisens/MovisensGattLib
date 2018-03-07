package com.movisens.movisensgattlib.attributes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.movisensgattlib.helper.AbstractBufferedAttribute;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class ColorTemperatureBuffered extends AbstractBufferedAttribute<ColorTemperatureData>
{

	public static final BufferedCharacteristic<ColorTemperatureBuffered, ColorTemperatureData> CHARACTERISTIC = MovisensCharacteristics.COLOR_TEMPERATURE_BUFFERED;
	
	public static final int periodLength = 60;
	private long time;
	private Long colorTemperature[];
	
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
		String[] names = {"colorTemperature"};
		return names;
	}

	@Override
	public String[] getValueUnits()
	{
		String[] names = {""};
		return names;
	}
	
	@Override
	public double[][] getValues()
	{
		int numSamples = colorTemperature.length;
		double[][] data = new double[numSamples][1];
		
		for(int i=0; i<numSamples; i++)
		{
			data[i][0] = colorTemperature[i];
		}
		
		return data;
	}

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
	public BufferedCharacteristic<ColorTemperatureBuffered, ColorTemperatureData> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		String result = "";
		for(int i=0; i<colorTemperature.length; i++)
		{
			result += "time = " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date((time + (periodLength * i)) * 1000)) + ", " + getColorTemperature()[i].toString() + " \r\n";
		}
		return result;
	}

	@Override
	public Iterable<ColorTemperatureData> getData()
	{
	    Vector<ColorTemperatureData> datas = new Vector<ColorTemperatureData>();
	    long now = new Date().getTime();
	    
	    for(int i=0; i<colorTemperature.length; i++)
	    {
	        datas.add(new ColorTemperatureData(now, (time + (periodLength * i)) * 1000, periodLength, CHARACTERISTIC, getColorTemperature()[i]));
	    }
	    
	    return datas;
	}
}
