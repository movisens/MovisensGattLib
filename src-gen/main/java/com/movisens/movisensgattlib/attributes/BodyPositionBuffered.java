package com.movisens.movisensgattlib.attributes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.movisensgattlib.helper.AbstractBufferedAttribute;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class BodyPositionBuffered extends AbstractBufferedAttribute<BodyPositionData>
{

	public static final BufferedCharacteristic<BodyPositionBuffered, BodyPositionData> CHARACTERISTIC = MovisensCharacteristics.BODY_POSITION_BUFFERED;
	
	public static final int periodLength = 60;
	private long time;
	private EnumBodyPosition bodyPosition[];
	
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
		String[] names = {"bodyPosition"};
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
		int numSamples = bodyPosition.length;
		double[][] data = new double[numSamples][1];
		
		for(int i=0; i<numSamples; i++)
		{
			data[i][0] = bodyPosition[i].getValue();
		}
		
		return data;
	}

	public EnumBodyPosition[] getBodyPosition()
	{
		return bodyPosition;
	}
	
	public String getBodyPositionUnit()
	{
		return "";
	}
	
	public BodyPositionBuffered(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		
		time = bb.getUint32();
		short numValues = bb.getUint8();
		
		bodyPosition = new EnumBodyPosition[numValues];
		
		for (int i = 0; i < numValues; i++)
		{
			bodyPosition[i] = EnumBodyPosition.getByValue(bb.getUint8());
		}
	}

	@Override
	public BufferedCharacteristic<BodyPositionBuffered, BodyPositionData> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		String result = "";
		for(int i=0; i<bodyPosition.length; i++)
		{
			result += "time = " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date((time + (periodLength * i)) * 1000)) + ", " + getBodyPosition()[i].toString() + " \r\n";
		}
		return result;
	}

	@Override
	public Iterable<BodyPositionData> getData()
	{
	    Vector<BodyPositionData> datas = new Vector<BodyPositionData>();
	    long now = new Date().getTime();
	    
	    for(int i=0; i<bodyPosition.length; i++)
	    {
	        datas.add(new BodyPositionData(now, (time + (periodLength * i)) * 1000, periodLength, getBodyPosition()[i]));
	    }
	    
	    return datas;
	}
}
