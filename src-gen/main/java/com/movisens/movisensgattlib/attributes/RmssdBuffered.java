package com.movisens.movisensgattlib.attributes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.movisensgattlib.helper.AbstractBufferedAttribute;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class RmssdBuffered extends AbstractBufferedAttribute<RmssdData>
{

	public static final BufferedCharacteristic<RmssdBuffered, RmssdData> CHARACTERISTIC = MovisensCharacteristics.RMSSD_BUFFERED;
	
	public static final int periodLength = 60;
	private long time;
	private Short rmssd[];
	
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
		String[] names = {"rmssd"};
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
		int numSamples = rmssd.length;
		double[][] data = new double[numSamples][1];
		
		for(int i=0; i<numSamples; i++)
		{
			data[i][0] = rmssd[i];
		}
		
		return data;
	}

	public Short[] getRmssd()
	{
		return rmssd;
	}
	
	public String getRmssdUnit()
	{
		return "";
	}
	
	public RmssdBuffered(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		
		time = bb.getUint32();
		short numValues = bb.getUint8();
		
		rmssd = new Short[numValues];
		
		for (int i = 0; i < numValues; i++)
		{
			rmssd[i] = bb.getInt16();
		}
	}

	@Override
	public BufferedCharacteristic<RmssdBuffered, RmssdData> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		String result = "";
		for(int i=0; i<rmssd.length; i++)
		{
			result += "time = " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date((time + (periodLength * i)) * 1000)) + ", " + getRmssd()[i].toString() + " \r\n";
		}
		return result;
	}

	@Override
	public Iterable<RmssdData> getData()
	{
	    Vector<RmssdData> datas = new Vector<RmssdData>();
	    long now = new Date().getTime();
	    
	    for(int i=0; i<rmssd.length; i++)
	    {
	        datas.add(new RmssdData(now, (time + (periodLength * i)) * 1000, periodLength, getRmssd()[i]));
	    }
	    
	    return datas;
	}
}
