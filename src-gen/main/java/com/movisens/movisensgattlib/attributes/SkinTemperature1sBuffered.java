package com.movisens.movisensgattlib.attributes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.movisensgattlib.helper.AbstractBufferedAttribute;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class SkinTemperature1sBuffered extends AbstractBufferedAttribute<SkinTemperature1sData>
{

	public static final BufferedCharacteristic<SkinTemperature1sBuffered, SkinTemperature1sData> CHARACTERISTIC = MovisensCharacteristics.SKIN_TEMPERATURE_1S_BUFFERED;
	
	public static final int periodLength = 1;
	private long time;
	private Double skinTemperature[];
	
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
		String[] names = {"skinTemperature"};
		return names;
	}

	@Override
	public String[] getValueUnits()
	{
		String[] names = {"°C"};
		return names;
	}
	
	@Override
	public double[][] getValues()
	{
		int numSamples = skinTemperature.length;
		double[][] data = new double[numSamples][1];
		
		for(int i=0; i<numSamples; i++)
		{
			data[i][0] = skinTemperature[i];
		}
		
		return data;
	}

	public Double[] getSkinTemperature()
	{
		return skinTemperature;
	}
	
	public String getSkinTemperatureUnit()
	{
		return "°C";
	}
	
	public SkinTemperature1sBuffered(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		
		time = bb.getUint32();
		short numValues = bb.getUint8();
		
		skinTemperature = new Double[numValues];
		
		for (int i = 0; i < numValues; i++)
		{
			skinTemperature[i] = ((double)bb.getInt16()) * 0.01;
		}
	}

	@Override
	public BufferedCharacteristic<SkinTemperature1sBuffered, SkinTemperature1sData> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		String result = "";
		for(int i=0; i<skinTemperature.length; i++)
		{
			result += "time = " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date((time + (periodLength * i)) * 1000)) + ", " + getSkinTemperature()[i].toString() + getSkinTemperatureUnit() + " \r\n";
		}
		return result;
	}

	@Override
	public Iterable<SkinTemperature1sData> getData()
	{
	    Vector<SkinTemperature1sData> datas = new Vector<SkinTemperature1sData>();
	    long now = new Date().getTime();
	    
	    for(int i=0; i<skinTemperature.length; i++)
	    {
	        datas.add(new SkinTemperature1sData(now, (time + (periodLength * i)) * 1000, periodLength, getSkinTemperature()[i]));
	    }
	    
	    return datas;
	}
}
