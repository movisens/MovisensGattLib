package com.movisens.movisensgattlib.attributes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.movisensgattlib.helper.AbstractBufferedAttribute;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class LightBuffered extends AbstractBufferedAttribute<LightData>
{

	public static final BufferedCharacteristic<LightBuffered, LightData> CHARACTERISTIC = MovisensCharacteristics.LIGHT_BUFFERED;
	
	public static final int periodLength = 60;
	private long time;
	private Long clear[];
	private Long ir[];
	
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
		String[] names = {"clear", "ir"};
		return names;
	}

	@Override
	public String[] getValueUnits()
	{
		String[] names = {"", ""};
		return names;
	}
	
	@Override
	public double[][] getValues()
	{
		int numSamples = clear.length;
		double[][] data = new double[numSamples][2];
		
		for(int i=0; i<numSamples; i++)
		{
			data[i][0] = clear[i];
			data[i][1] = ir[i];
		}
		
		return data;
	}

	public Long[] getClear()
	{
		return clear;
	}
	
	public String getClearUnit()
	{
		return "";
	}
	
	public Long[] getIr()
	{
		return ir;
	}
	
	public String getIrUnit()
	{
		return "";
	}
	
	public LightBuffered(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		
		time = bb.getUint32();
		short numValues = bb.getUint8();
		
		clear = new Long[numValues];
		ir = new Long[numValues];
		
		for (int i = 0; i < numValues; i++)
		{
			clear[i] = bb.getUint32();
			ir[i] = bb.getUint32();
		}
	}

	@Override
	public BufferedCharacteristic<LightBuffered, LightData> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		String result = "";
		for(int i=0; i<clear.length; i++)
		{
			result += "time = " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date((time + (periodLength * i)) * 1000)) + ", " + "clear = " + getClear()[i] + ", " + "ir = " + getIr()[i] + " \r\n";
		}
		return result;
	}

	@Override
	public Iterable<LightData> getData()
	{
	    Vector<LightData> datas = new Vector<LightData>();
	    long now = new Date().getTime();
	    
	    for(int i=0; i<clear.length; i++)
	    {
	        datas.add(new LightData(now, (time + (periodLength * i)) * 1000, periodLength, getClear()[i], getIr()[i]));
	    }
	    
	    return datas;
	}
}
