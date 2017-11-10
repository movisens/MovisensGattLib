package com.movisens.movisensgattlib.attributes;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.movisensgattlib.helper.BufferedAttribute;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class BodyPositionBuffered extends AbstractReadAttribute implements BufferedAttribute
{

	public static final Characteristic CHARACTERISTIC = MovisensCharacteristics.BODY_POSITION_BUFFERED;
	
	public static final int periodLength = 60;
	private long time;
	private EnumBodyPosition bodyPosition[];
	
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
	
	public EnumBodyPosition[] getBodyPosition()
	{
		return bodyPosition;
	}
	
	public String getBodyPositionUnit()
	{
		return "";
	}
	
	public BodyPositionBuffered(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		
		time = bb.getUint32();
		short numValues = bb.getUint8();
		
		bodyPosition = new EnumBodyPosition[numValues];
		
		for (int i = 0; i < numValues; i++)
		{
			bodyPosition[i] = EnumBodyPosition.getByValue(bb.getUint8());
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
		for(int i=0; i<bodyPosition.length; i++)
		{
			result += "Body Position Buffered: " + "time = " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date((time + (periodLength * i)) * 1000)) + ", " + "bodyPosition = " + getBodyPosition()[i] + "\r\n";
		}
		return result;
	}
}
