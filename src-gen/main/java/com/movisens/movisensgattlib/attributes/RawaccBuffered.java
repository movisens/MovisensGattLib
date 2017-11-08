package com.movisens.movisensgattlib.attributes;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class RawaccBuffered extends AbstractReadAttribute
{

	public static final Characteristic CHARACTERISTIC = MovisensCharacteristics.RAWACC_BUFFERED;
	
	public static final int periodLength = 0;
	private long time;
	private Short x[];
	private Short y[];
	private Short z[];
	
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
	
	public RawaccBuffered(byte[] data)
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
			x[i] = bb.getInt16();
			y[i] = bb.getInt16();
			z[i] = bb.getInt16();
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
		for(int i=0; i<x.length; i++)
		{
			result += "Rawacc Buffered: " + "time = " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date((time + (periodLength * i)) * 1000)) + ", " + "x = " + getX()[i] + ", " + "y = " + getY()[i] + ", " + "z = " + getZ()[i] + "\r\n";
		}
		return result;
	}
}
