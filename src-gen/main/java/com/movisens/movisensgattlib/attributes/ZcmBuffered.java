package com.movisens.movisensgattlib.attributes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.movisensgattlib.helper.AbstractBufferedAttribute;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class ZcmBuffered extends AbstractBufferedAttribute<ZcmData>
{

	public static final BufferedCharacteristic<ZcmBuffered, ZcmData> CHARACTERISTIC = MovisensCharacteristics.ZCM_BUFFERED;
	
	public static final int periodLength = 60;
	private long time;
	private Short zcm[];
	
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
		String[] names = {"zcm"};
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
		int numSamples = zcm.length;
		double[][] data = new double[numSamples][1];
		
		for(int i=0; i<numSamples; i++)
		{
			data[i][0] = zcm[i];
		}
		
		return data;
	}

	public Short[] getZcm()
	{
		return zcm;
	}
	
	public String getZcmUnit()
	{
		return "";
	}
	
	public ZcmBuffered(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		
		time = bb.getUint32();
		short numValues = bb.getUint8();
		
		zcm = new Short[numValues];
		
		for (int i = 0; i < numValues; i++)
		{
			zcm[i] = bb.getInt16();
		}
	}

	@Override
	public BufferedCharacteristic<ZcmBuffered, ZcmData> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		String result = "";
		for(int i=0; i<zcm.length; i++)
		{
			result += "time = " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date((time + (periodLength * i)) * 1000)) + ", " + getZcm()[i].toString() + " \r\n";
		}
		return result;
	}

	@Override
	public Iterable<ZcmData> getData()
	{
	    Vector<ZcmData> datas = new Vector<ZcmData>();
	    long now = new Date().getTime();
	    
	    for(int i=0; i<zcm.length; i++)
	    {
	        datas.add(new ZcmData(now, (time + (periodLength * i)) * 1000, periodLength, CHARACTERISTIC, getZcm()[i]));
	    }
	    
	    return datas;
	}
}
