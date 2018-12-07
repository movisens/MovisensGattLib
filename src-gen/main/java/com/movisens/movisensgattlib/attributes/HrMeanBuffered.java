package com.movisens.movisensgattlib.attributes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.movisensgattlib.helper.AbstractBufferedAttribute;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class HrMeanBuffered extends AbstractBufferedAttribute<HrMeanData>
{

	public static final BufferedCharacteristic<HrMeanBuffered, HrMeanData> CHARACTERISTIC = MovisensCharacteristics.HR_MEAN_BUFFERED;
	
	public static final int periodLength = 60;
	private long time;
	private Double hrMean[];
	
	public int getBitPosition()
	{
		return 6;
	}
	
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
		String[] names = {"hrMean"};
		return names;
	}

	@Override
	public String[] getValueUnits()
	{
		String[] names = {"1/min"};
		return names;
	}
	
	@Override
	public double[][] getValues()
	{
		int numSamples = hrMean.length;
		double[][] data = new double[numSamples][1];
		
		for(int i=0; i<numSamples; i++)
		{
			data[i][0] = hrMean[i];
		}
		
		return data;
	}

	public Double[] getHrMean()
	{
		return hrMean;
	}
	
	public String getHrMeanUnit()
	{
		return "1/min";
	}
	
	public HrMeanBuffered(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		
		time = bb.getUint32();
		short numValues = bb.getUint8();
		
		hrMean = new Double[numValues];
		
		for (int i = 0; i < numValues; i++)
		{
			hrMean[i] = new Double(bb.getInt16());
		}
	}

	@Override
	public BufferedCharacteristic<HrMeanBuffered, HrMeanData> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		String result = "";
		for(int i=0; i<hrMean.length; i++)
		{
			result += "time = " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date((time + (periodLength * i)) * 1000)) + ", " + getHrMean()[i].toString() + getHrMeanUnit() + " \r\n";
		}
		return result;
	}

	@Override
	public Iterable<HrMeanData> getData()
	{
	    Vector<HrMeanData> datas = new Vector<HrMeanData>();
	    long now = new Date().getTime();
	    
	    for(int i=0; i<hrMean.length; i++)
	    {
	        datas.add(new HrMeanData(now, (time + (periodLength * i)) * 1000, periodLength, getHrMean()[i]));
	    }
	    
	    return datas;
	}
}
