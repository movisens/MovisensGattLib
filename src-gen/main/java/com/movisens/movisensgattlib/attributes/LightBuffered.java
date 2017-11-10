package com.movisens.movisensgattlib.attributes;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class LightBuffered extends AbstractReadAttribute
{

	public static final Characteristic CHARACTERISTIC = MovisensCharacteristics.LIGHT_BUFFERED;
	
	public static final int periodLength = 60;
	private long time;
	private Long red[];
	private Long green[];
	private Long blue[];
	private Long clear[];
	private Long ir[];
	
	public Long[] getRed()
	{
		return red;
	}
	
	public String getRedUnit()
	{
		return "";
	}
	
	public Long[] getGreen()
	{
		return green;
	}
	
	public String getGreenUnit()
	{
		return "";
	}
	
	public Long[] getBlue()
	{
		return blue;
	}
	
	public String getBlueUnit()
	{
		return "";
	}
	
	public Long[] getClear()
	{
		return clear;
	}
	
	public String getClearUnit()
	{
		return "";
	}
	
	public Long[] getIr()
	{
		return ir;
	}
	
	public String getIrUnit()
	{
		return "";
	}
	
	public LightBuffered(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		
		time = bb.getUint32();
		short numValues = bb.getUint8();
		
		red = new Long[numValues];
		green = new Long[numValues];
		blue = new Long[numValues];
		clear = new Long[numValues];
		ir = new Long[numValues];
		
		for (int i = 0; i < numValues; i++)
		{
			red[i] = bb.getUint32();
			green[i] = bb.getUint32();
			blue[i] = bb.getUint32();
			clear[i] = bb.getUint32();
			ir[i] = bb.getUint32();
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
		for(int i=0; i<red.length; i++)
		{
			result += "Light Buffered: " + "time = " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date((time + (periodLength * i)) * 1000)) + ", " + "red = " + getRed()[i] + ", " + "green = " + getGreen()[i] + ", " + "blue = " + getBlue()[i] + ", " + "clear = " + getClear()[i] + ", " + "ir = " + getIr()[i] + "\r\n";
		}
		return result;
	}
}
