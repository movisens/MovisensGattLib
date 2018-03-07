package com.movisens.movisensgattlib.attributes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.movisensgattlib.helper.AbstractBufferedAttribute;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class StepsBuffered extends AbstractBufferedAttribute<StepsData>
{

	public static final BufferedCharacteristic<StepsBuffered, StepsData> CHARACTERISTIC = MovisensCharacteristics.STEPS_BUFFERED;
	
	public static final int periodLength = 60;
	private long time;
	private Integer steps[];
	
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
		String[] names = {"steps"};
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
		int numSamples = steps.length;
		double[][] data = new double[numSamples][1];
		
		for(int i=0; i<numSamples; i++)
		{
			data[i][0] = steps[i];
		}
		
		return data;
	}

	public Integer[] getSteps()
	{
		return steps;
	}
	
	public String getStepsUnit()
	{
		return "";
	}
	
	public StepsBuffered(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		
		time = bb.getUint32();
		short numValues = bb.getUint8();
		
		steps = new Integer[numValues];
		
		for (int i = 0; i < numValues; i++)
		{
			steps[i] = bb.getUint16();
		}
	}

	@Override
	public BufferedCharacteristic<StepsBuffered, StepsData> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		String result = "";
		for(int i=0; i<steps.length; i++)
		{
			result += "time = " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date((time + (periodLength * i)) * 1000)) + ", " + getSteps()[i].toString() + " \r\n";
		}
		return result;
	}

	@Override
	public Iterable<StepsData> getData()
	{
	    Vector<StepsData> datas = new Vector<StepsData>();
	    long now = new Date().getTime();
	    
	    for(int i=0; i<steps.length; i++)
	    {
	        datas.add(new StepsData(now, (time + (periodLength * i)) * 1000, periodLength, CHARACTERISTIC, getSteps()[i]));
	    }
	    
	    return datas;
	}
}
