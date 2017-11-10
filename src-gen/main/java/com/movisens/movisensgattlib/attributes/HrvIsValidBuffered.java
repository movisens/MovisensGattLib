package com.movisens.movisensgattlib.attributes;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.movisensgattlib.helper.BufferedAttribute;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class HrvIsValidBuffered extends AbstractReadAttribute implements BufferedAttribute
{

	public static final Characteristic CHARACTERISTIC = MovisensCharacteristics.HRV_IS_VALID_BUFFERED;
	
	public static final int periodLength = 60;
	private long time;
	private Boolean hrvIsValid[];
	
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
	
	public Boolean[] getHrvIsValid()
	{
		return hrvIsValid;
	}
	
	public String getHrvIsValidUnit()
	{
		return "";
	}
	
	public HrvIsValidBuffered(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		
		time = bb.getUint32();
		short numValues = bb.getUint8();
		
		hrvIsValid = new Boolean[numValues];
		
		for (int i = 0; i < numValues; i++)
		{
			hrvIsValid[i] = bb.getBoolean();
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
		for(int i=0; i<hrvIsValid.length; i++)
		{
			result += "Hrv Is Valid Buffered: " + "time = " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date((time + (periodLength * i)) * 1000)) + ", " + "hrvIsValid = " + getHrvIsValid()[i] + "\r\n";
		}
		return result;
	}
}
