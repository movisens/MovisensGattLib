package com.movisens.movisensgattlib.attributes;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.movisensgattlib.helper.BufferedAttribute;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class HrMeanBuffered extends AbstractReadAttribute implements BufferedAttribute
{

	public static final Characteristic CHARACTERISTIC = MovisensCharacteristics.HR_MEAN_BUFFERED;
	
	public static final int periodLength = 60;
	private long time;
	private Short hrMean[];
	
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
	
	public Short[] getHrMean()
	{
		return hrMean;
	}
	
	public String getHrMeanUnit()
	{
		return "";
	}
	
	public HrMeanBuffered(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		
		time = bb.getUint32();
		short numValues = bb.getUint8();
		
		hrMean = new Short[numValues];
		
		for (int i = 0; i < numValues; i++)
		{
			hrMean[i] = bb.getInt16();
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
		for(int i=0; i<hrMean.length; i++)
		{
			result += "Hr Mean Buffered: " + "time = " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date((time + (periodLength * i)) * 1000)) + ", " + "hrMean = " + getHrMean()[i] + "\r\n";
		}
		return result;
	}
}
