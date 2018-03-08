package com.movisens.movisensgattlib.attributes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.movisensgattlib.helper.AbstractBufferedAttribute;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class ActivityclassBuffered extends AbstractBufferedAttribute<ActivityclassData>
{

	public static final BufferedCharacteristic<ActivityclassBuffered, ActivityclassData> CHARACTERISTIC = MovisensCharacteristics.ACTIVITYCLASS_BUFFERED;
	
	public static final int periodLength = 60;
	private long time;
	private Short activityClass[];
	
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
		String[] names = {"activityClass"};
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
		int numSamples = activityClass.length;
		double[][] data = new double[numSamples][1];
		
		for(int i=0; i<numSamples; i++)
		{
			data[i][0] = activityClass[i];
		}
		
		return data;
	}

	public Short[] getActivityClass()
	{
		return activityClass;
	}
	
	public String getActivityClassUnit()
	{
		return "";
	}
	
	public ActivityclassBuffered(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		
		time = bb.getUint32();
		short numValues = bb.getUint8();
		
		activityClass = new Short[numValues];
		
		for (int i = 0; i < numValues; i++)
		{
			activityClass[i] = bb.getInt16();
		}
	}

	@Override
	public BufferedCharacteristic<ActivityclassBuffered, ActivityclassData> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		String result = "";
		for(int i=0; i<activityClass.length; i++)
		{
			result += "time = " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date((time + (periodLength * i)) * 1000)) + ", " + getActivityClass()[i].toString() + " \r\n";
		}
		return result;
	}

	@Override
	public Iterable<ActivityclassData> getData()
	{
	    Vector<ActivityclassData> datas = new Vector<ActivityclassData>();
	    long now = new Date().getTime();
	    
	    for(int i=0; i<activityClass.length; i++)
	    {
	        datas.add(new ActivityclassData(now, (time + (periodLength * i)) * 1000, periodLength, getActivityClass()[i]));
	    }
	    
	    return datas;
	}
}
