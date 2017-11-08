package com.movisens.movisensgattlib.attributes;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class StepsBuffered extends AbstractReadAttribute
{

	public static final Characteristic CHARACTERISTIC = MovisensCharacteristics.STEPS_BUFFERED;
	
	public static final int periodLength = 60;
	private long time;
	private Integer steps[];
	
	public Integer[] getSteps()
	{
		return steps;
	}
	
	public String getStepsUnit()
	{
		return "";
	}
	
	public StepsBuffered(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		
		time = bb.getUint32();
		short numValues = bb.getUint8();
		
		steps = new Integer[numValues];
		
		for (int i = 0; i < numValues; i++)
		{
			steps[i] = bb.getUint16();
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
		for(int i=0; i<steps.length; i++)
		{
			result += "Steps Buffered: " + "time = " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date((time + (periodLength * i)) * 1000)) + ", " + "steps = " + getSteps()[i] + "\r\n";
		}
		return result;
	}
}
