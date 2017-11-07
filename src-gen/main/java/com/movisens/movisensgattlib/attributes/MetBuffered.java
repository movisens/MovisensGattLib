package com.movisens.movisensgattlib.attributes;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class MetBuffered extends AbstractReadAttribute
{

	public static final Characteristic CHARACTERISTIC = MovisensCharacteristics.MET_BUFFERED;
	
	public static final int periodLength = 60;
	private long time;
	private Double met[];
	
	public Double[] getMet()
	{
		return met;
	}
	
	public String getMetUnit()
	{
		return "";
	}
	
	public MetBuffered(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		
		time = bb.getUint32();
		short numValues = bb.getUint8();
		
		met = new Double[numValues];
		
		for (int i = 0; i < numValues; i++)
		{
			met[i] = ((double)bb.getUint16()) * 0.00390625;
		}
	}

	@Override
	public Characteristic getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		String result = "";
		for(int i=0; i<met.length; i++)
		{
			result += "Met Buffered: " + "time=" + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date((time + (periodLength * i)) * 1000)) + "met = " + getMet()[i] + " " + getMetUnit() + "\r\n";
		}
		return result;
	}
}
