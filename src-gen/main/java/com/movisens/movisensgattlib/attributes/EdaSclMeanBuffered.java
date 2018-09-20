package com.movisens.movisensgattlib.attributes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.movisensgattlib.helper.AbstractBufferedAttribute;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class EdaSclMeanBuffered extends AbstractBufferedAttribute<EdaSclMeanData>
{

	public static final BufferedCharacteristic<EdaSclMeanBuffered, EdaSclMeanData> CHARACTERISTIC = MovisensCharacteristics.EDA_SCL_MEAN_BUFFERED;
	
	public static final int periodLength = 60;
	private long time;
	private Double edaSclMean[];
	
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
		String[] names = {"edaSclMean"};
		return names;
	}

	@Override
	public String[] getValueUnits()
	{
		String[] names = {"µS"};
		return names;
	}
	
	@Override
	public double[][] getValues()
	{
		int numSamples = edaSclMean.length;
		double[][] data = new double[numSamples][1];
		
		for(int i=0; i<numSamples; i++)
		{
			data[i][0] = edaSclMean[i];
		}
		
		return data;
	}

	public Double[] getEdaSclMean()
	{
		return edaSclMean;
	}
	
	public String getEdaSclMeanUnit()
	{
		return "µS";
	}
	
	public EdaSclMeanBuffered(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		
		time = bb.getUint32();
		short numValues = bb.getUint8();
		
		edaSclMean = new Double[numValues];
		
		for (int i = 0; i < numValues; i++)
		{
			edaSclMean[i] = ((double)bb.getUint16()) * 0.0030518509475997192;
		}
	}

	@Override
	public BufferedCharacteristic<EdaSclMeanBuffered, EdaSclMeanData> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		String result = "";
		for(int i=0; i<edaSclMean.length; i++)
		{
			result += "time = " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date((time + (periodLength * i)) * 1000)) + ", " + getEdaSclMean()[i].toString() + getEdaSclMeanUnit() + " \r\n";
		}
		return result;
	}

	@Override
	public Iterable<EdaSclMeanData> getData()
	{
	    Vector<EdaSclMeanData> datas = new Vector<EdaSclMeanData>();
	    long now = new Date().getTime();
	    
	    for(int i=0; i<edaSclMean.length; i++)
	    {
	        datas.add(new EdaSclMeanData(now, (time + (periodLength * i)) * 1000, periodLength, getEdaSclMean()[i]));
	    }
	    
	    return datas;
	}
}
