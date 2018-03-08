package com.movisens.movisensgattlib.attributes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.movisensgattlib.helper.AbstractBufferedAttribute;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class IlluminationBuffered extends AbstractBufferedAttribute<IlluminationData>
{

	public static final BufferedCharacteristic<IlluminationBuffered, IlluminationData> CHARACTERISTIC = MovisensCharacteristics.ILLUMINATION_BUFFERED;
	
	public static final int periodLength = 60;
	private long time;
	private Long illumination[];
	
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
		String[] names = {"illumination"};
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
		int numSamples = illumination.length;
		double[][] data = new double[numSamples][1];
		
		for(int i=0; i<numSamples; i++)
		{
			data[i][0] = illumination[i];
		}
		
		return data;
	}

	public Long[] getIllumination()
	{
		return illumination;
	}
	
	public String getIlluminationUnit()
	{
		return "";
	}
	
	public IlluminationBuffered(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		
		time = bb.getUint32();
		short numValues = bb.getUint8();
		
		illumination = new Long[numValues];
		
		for (int i = 0; i < numValues; i++)
		{
			illumination[i] = bb.getUint32();
		}
	}

	@Override
	public BufferedCharacteristic<IlluminationBuffered, IlluminationData> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		String result = "";
		for(int i=0; i<illumination.length; i++)
		{
			result += "time = " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date((time + (periodLength * i)) * 1000)) + ", " + getIllumination()[i].toString() + " \r\n";
		}
		return result;
	}

	@Override
	public Iterable<IlluminationData> getData()
	{
	    Vector<IlluminationData> datas = new Vector<IlluminationData>();
	    long now = new Date().getTime();
	    
	    for(int i=0; i<illumination.length; i++)
	    {
	        datas.add(new IlluminationData(now, (time + (periodLength * i)) * 1000, periodLength, getIllumination()[i]));
	    }
	    
	    return datas;
	}
}
