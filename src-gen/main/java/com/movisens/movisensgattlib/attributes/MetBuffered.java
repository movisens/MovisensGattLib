package com.movisens.movisensgattlib.attributes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.movisensgattlib.helper.AbstractBufferedAttribute;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class MetBuffered extends AbstractBufferedAttribute<MetData>
{

	public static final BufferedCharacteristic<MetBuffered, MetData> CHARACTERISTIC = MovisensCharacteristics.MET_BUFFERED;
	
	public static final int periodLength = 60;
	private long time;
	private Double met[];
	
	public int getBitPosition()
	{
		return 8;
	}
	
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
		String[] names = {"met"};
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
		int numSamples = met.length;
		double[][] data = new double[numSamples][1];
		
		for(int i=0; i<numSamples; i++)
		{
			data[i][0] = met[i];
		}
		
		return data;
	}

	public Double[] getMet()
	{
		return met;
	}
	
	public String getMetUnit()
	{
		return "";
	}
	
	public MetBuffered(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		
		time = bb.getUint32();
		short numValues = bb.getUint8();
		
		met = new Double[numValues];
		
		for (int i = 0; i < numValues; i++)
		{
			met[i] = ((double)bb.getUint16()) * 0.00390625;
		}
	}

	@Override
	public BufferedCharacteristic<MetBuffered, MetData> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		String result = "";
		for(int i=0; i<met.length; i++)
		{
			result += "time = " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date((time + (periodLength * i)) * 1000)) + ", " + getMet()[i].toString() + " \r\n";
		}
		return result;
	}

	@Override
	public Iterable<MetData> getData()
	{
	    Vector<MetData> datas = new Vector<MetData>();
	    long now = new Date().getTime();
	    
	    for(int i=0; i<met.length; i++)
	    {
	        datas.add(new MetData(now, (time + (periodLength * i)) * 1000, periodLength, getMet()[i]));
	    }
	    
	    return datas;
	}
}
