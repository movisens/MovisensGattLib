package com.movisens.movisensgattlib.attributes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.movisensgattlib.helper.AbstractBufferedAttribute;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class ChargingBuffered extends AbstractBufferedAttribute<ChargingData>
{

	public static final BufferedCharacteristic<ChargingBuffered, ChargingData> CHARACTERISTIC = MovisensCharacteristics.CHARGING_BUFFERED;
	
	public static final int periodLength = 60;
	private long time;
	private Boolean charging[];
	
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
		String[] names = {"charging"};
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
		int numSamples = charging.length;
		double[][] data = new double[numSamples][1];
		
		for(int i=0; i<numSamples; i++)
		{
			data[i][0] = charging[i] ? 1.0 : 0.0;
		}
		
		return data;
	}

	public Boolean[] getCharging()
	{
		return charging;
	}
	
	public String getChargingUnit()
	{
		return "";
	}
	
	public ChargingBuffered(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		
		time = bb.getUint32();
		short numValues = bb.getUint8();
		
		charging = new Boolean[numValues];
		
		for (int i = 0; i < numValues; i++)
		{
			charging[i] = bb.getBoolean();
		}
	}

	@Override
	public BufferedCharacteristic<ChargingBuffered, ChargingData> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		String result = "";
		for(int i=0; i<charging.length; i++)
		{
			result += "time = " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date((time + (periodLength * i)) * 1000)) + ", " + getCharging()[i].toString() + " \r\n";
		}
		return result;
	}

	@Override
	public Iterable<ChargingData> getData()
	{
	    Vector<ChargingData> datas = new Vector<ChargingData>();
	    long now = new Date().getTime();
	    
	    for(int i=0; i<charging.length; i++)
	    {
	        datas.add(new ChargingData(now, (time + (periodLength * i)) * 1000, periodLength, getCharging()[i]));
	    }
	    
	    return datas;
	}
}
