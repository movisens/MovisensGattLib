package com.movisens.movisensgattlib.attributes;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class EdaSclMeanBuffered extends AbstractReadAttribute
{

	public static final Characteristic CHARACTERISTIC = MovisensCharacteristics.EDA_SCL_MEAN_BUFFERED;
	
	public static final int periodLength = 60;
	private long time;
	private Integer edaSclMean[];
	
	public Integer[] getEdaSclMean()
	{
		return edaSclMean;
	}
	
	public String getEdaSclMeanUnit()
	{
		return "";
	}
	
	public EdaSclMeanBuffered(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		
		time = bb.getUint32();
		short numValues = bb.getUint8();
		
		edaSclMean = new Integer[numValues];
		
		for (int i = 0; i < numValues; i++)
		{
			edaSclMean[i] = bb.getUint16();
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
		for(int i=0; i<edaSclMean.length; i++)
		{
			result += "Eda Scl Mean Buffered: " + "time = " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date((time + (periodLength * i)) * 1000)) + ", " + "edaSclMean = " + getEdaSclMean()[i] + "\r\n";
		}
		return result;
	}
}
