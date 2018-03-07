package com.movisens.movisensgattlib.attributes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.movisensgattlib.helper.AbstractBufferedAttribute;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class EdaCountsBuffered extends AbstractBufferedAttribute<EdaCountsData>
{

	public static final BufferedCharacteristic<EdaCountsBuffered, EdaCountsData> CHARACTERISTIC = MovisensCharacteristics.EDA_COUNTS_BUFFERED;
	
	public static final int periodLength = 60;
	private long time;
	private Integer edaCounts[];
	
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
		String[] names = {"edaCounts"};
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
		int numSamples = edaCounts.length;
		double[][] data = new double[numSamples][1];
		
		for(int i=0; i<numSamples; i++)
		{
			data[i][0] = edaCounts[i];
		}
		
		return data;
	}

	public Integer[] getEdaCounts()
	{
		return edaCounts;
	}
	
	public String getEdaCountsUnit()
	{
		return "";
	}
	
	public EdaCountsBuffered(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		
		time = bb.getUint32();
		short numValues = bb.getUint8();
		
		edaCounts = new Integer[numValues];
		
		for (int i = 0; i < numValues; i++)
		{
			edaCounts[i] = bb.getUint16();
		}
	}

	@Override
	public BufferedCharacteristic<EdaCountsBuffered, EdaCountsData> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		String result = "";
		for(int i=0; i<edaCounts.length; i++)
		{
			result += "time = " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date((time + (periodLength * i)) * 1000)) + ", " + getEdaCounts()[i].toString() + " \r\n";
		}
		return result;
	}

	@Override
	public Iterable<EdaCountsData> getData()
	{
	    Vector<EdaCountsData> datas = new Vector<EdaCountsData>();
	    long now = new Date().getTime();
	    
	    for(int i=0; i<edaCounts.length; i++)
	    {
	        datas.add(new EdaCountsData(now, (time + (periodLength * i)) * 1000, periodLength, CHARACTERISTIC, getEdaCounts()[i]));
	    }
	    
	    return datas;
	}
}
