package com.movisens.movisensgattlib.attributes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.movisensgattlib.helper.AbstractBufferedAttribute;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class PimBuffered extends AbstractBufferedAttribute<PimData>
{

	public static final BufferedCharacteristic<PimBuffered, PimData> CHARACTERISTIC = MovisensCharacteristics.PIM_BUFFERED;
	
	public static final int periodLength = 60;
	private long time;
	private Short pim[];
	
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
		String[] names = {"pim"};
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
		int numSamples = pim.length;
		double[][] data = new double[numSamples][1];
		
		for(int i=0; i<numSamples; i++)
		{
			data[i][0] = pim[i];
		}
		
		return data;
	}

	public Short[] getPim()
	{
		return pim;
	}
	
	public String getPimUnit()
	{
		return "";
	}
	
	public PimBuffered(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		
		time = bb.getUint32();
		short numValues = bb.getUint8();
		
		pim = new Short[numValues];
		
		for (int i = 0; i < numValues; i++)
		{
			pim[i] = bb.getInt16();
		}
	}

	@Override
	public BufferedCharacteristic<PimBuffered, PimData> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		String result = "";
		for(int i=0; i<pim.length; i++)
		{
			result += "time = " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date((time + (periodLength * i)) * 1000)) + ", " + getPim()[i].toString() + " \r\n";
		}
		return result;
	}

	@Override
	public Iterable<PimData> getData()
	{
	    Vector<PimData> datas = new Vector<PimData>();
	    long now = new Date().getTime();
	    
	    for(int i=0; i<pim.length; i++)
	    {
	        datas.add(new PimData(now, (time + (periodLength * i)) * 1000, periodLength, CHARACTERISTIC, getPim()[i]));
	    }
	    
	    return datas;
	}
}
