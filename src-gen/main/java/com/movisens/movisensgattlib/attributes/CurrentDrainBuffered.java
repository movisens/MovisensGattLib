package com.movisens.movisensgattlib.attributes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.movisensgattlib.helper.AbstractBufferedAttribute;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class CurrentDrainBuffered extends AbstractBufferedAttribute<CurrentDrainData>
{

	public static final BufferedCharacteristic<CurrentDrainBuffered, CurrentDrainData> CHARACTERISTIC = MovisensCharacteristics.CURRENT_DRAIN_BUFFERED;
	
	public static final int periodLength = 60;
	private long time;
	private Double currentDrain[];
	
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
		String[] names = {"currentDrain"};
		return names;
	}

	@Override
	public String[] getValueUnits()
	{
		String[] names = {"mA"};
		return names;
	}
	
	@Override
	public double[][] getValues()
	{
		int numSamples = currentDrain.length;
		double[][] data = new double[numSamples][1];
		
		for(int i=0; i<numSamples; i++)
		{
			data[i][0] = currentDrain[i];
		}
		
		return data;
	}

	public Double[] getCurrentDrain()
	{
		return currentDrain;
	}
	
	public String getCurrentDrainUnit()
	{
		return "mA";
	}
	
	public CurrentDrainBuffered(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		
		time = bb.getUint32();
		short numValues = bb.getUint8();
		
		currentDrain = new Double[numValues];
		
		for (int i = 0; i < numValues; i++)
		{
			currentDrain[i] = ((double)bb.getInt32()) * 0.01;
		}
	}

	@Override
	public BufferedCharacteristic<CurrentDrainBuffered, CurrentDrainData> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		String result = "";
		for(int i=0; i<currentDrain.length; i++)
		{
			result += "time = " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date((time + (periodLength * i)) * 1000)) + ", " + getCurrentDrain()[i].toString() + getCurrentDrainUnit() + " \r\n";
		}
		return result;
	}

	@Override
	public Iterable<CurrentDrainData> getData()
	{
	    Vector<CurrentDrainData> datas = new Vector<CurrentDrainData>();
	    long now = new Date().getTime();
	    
	    for(int i=0; i<currentDrain.length; i++)
	    {
	        datas.add(new CurrentDrainData(now, (time + (periodLength * i)) * 1000, periodLength, getCurrentDrain()[i]));
	    }
	    
	    return datas;
	}
}
