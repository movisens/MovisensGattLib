package com.movisens.movisensgattlib.attributes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.movisensgattlib.helper.AbstractBufferedAttribute;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class InclinationBuffered extends AbstractBufferedAttribute<InclinationData>
{

	public static final BufferedCharacteristic<InclinationBuffered, InclinationData> CHARACTERISTIC = MovisensCharacteristics.INCLINATION_BUFFERED;
	
	public static final int periodLength = 60;
	private long time;
	private Short x[];
	private Short y[];
	private Short z[];
	
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
		String[] names = {"x", "y", "z"};
		return names;
	}

	@Override
	public String[] getValueUnits()
	{
		String[] names = {"", "", ""};
		return names;
	}
	
	@Override
	public double[][] getValues()
	{
		int numSamples = x.length;
		double[][] data = new double[numSamples][3];
		
		for(int i=0; i<numSamples; i++)
		{
			data[i][0] = x[i];
			data[i][1] = y[i];
			data[i][2] = z[i];
		}
		
		return data;
	}

	public Short[] getX()
	{
		return x;
	}
	
	public String getXUnit()
	{
		return "";
	}
	
	public Short[] getY()
	{
		return y;
	}
	
	public String getYUnit()
	{
		return "";
	}
	
	public Short[] getZ()
	{
		return z;
	}
	
	public String getZUnit()
	{
		return "";
	}
	
	public InclinationBuffered(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		
		time = bb.getUint32();
		short numValues = bb.getUint8();
		
		x = new Short[numValues];
		y = new Short[numValues];
		z = new Short[numValues];
		
		for (int i = 0; i < numValues; i++)
		{
			x[i] = bb.getUint8();
			y[i] = bb.getUint8();
			z[i] = bb.getUint8();
		}
	}

	@Override
	public BufferedCharacteristic<InclinationBuffered, InclinationData> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		String result = "";
		for(int i=0; i<x.length; i++)
		{
			result += "time = " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date((time + (periodLength * i)) * 1000)) + ", " + "x = " + getX()[i] + ", " + "y = " + getY()[i] + ", " + "z = " + getZ()[i] + " \r\n";
		}
		return result;
	}

	@Override
	public Iterable<InclinationData> getData()
	{
	    Vector<InclinationData> datas = new Vector<InclinationData>();
	    long now = new Date().getTime();
	    
	    for(int i=0; i<x.length; i++)
	    {
	        datas.add(new InclinationData(now, (time + (periodLength * i)) * 1000, periodLength, getX()[i], getY()[i], getZ()[i]));
	    }
	    
	    return datas;
	}
}
