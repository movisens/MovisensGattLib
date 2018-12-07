package com.movisens.movisensgattlib.attributes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.movisensgattlib.helper.AbstractBufferedAttribute;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class BatteryLevelBuffered extends AbstractBufferedAttribute<BatteryLevelData>
{

	public static final BufferedCharacteristic<BatteryLevelBuffered, BatteryLevelData> CHARACTERISTIC = MovisensCharacteristics.BATTERY_LEVEL_BUFFERED;
	
	public static final int periodLength = 60;
	private long time;
	private Double level[];
	
	public int getBitPosition()
	{
		return 0;
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
		String[] names = {"level"};
		return names;
	}

	@Override
	public String[] getValueUnits()
	{
		String[] names = {"%"};
		return names;
	}
	
	@Override
	public double[][] getValues()
	{
		int numSamples = level.length;
		double[][] data = new double[numSamples][1];
		
		for(int i=0; i<numSamples; i++)
		{
			data[i][0] = level[i];
		}
		
		return data;
	}

	public Double[] getLevel()
	{
		return level;
	}
	
	public String getLevelUnit()
	{
		return "%";
	}
	
	public BatteryLevelBuffered(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		
		time = bb.getUint32();
		short numValues = bb.getUint8();
		
		level = new Double[numValues];
		
		for (int i = 0; i < numValues; i++)
		{
			level[i] = new Double(bb.getUint8());
		}
	}

	@Override
	public BufferedCharacteristic<BatteryLevelBuffered, BatteryLevelData> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		String result = "";
		for(int i=0; i<level.length; i++)
		{
			result += "time = " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date((time + (periodLength * i)) * 1000)) + ", " + getLevel()[i].toString() + getLevelUnit() + " \r\n";
		}
		return result;
	}

	@Override
	public Iterable<BatteryLevelData> getData()
	{
	    Vector<BatteryLevelData> datas = new Vector<BatteryLevelData>();
	    long now = new Date().getTime();
	    
	    for(int i=0; i<level.length; i++)
	    {
	        datas.add(new BatteryLevelData(now, (time + (periodLength * i)) * 1000, periodLength, getLevel()[i]));
	    }
	    
	    return datas;
	}
}
