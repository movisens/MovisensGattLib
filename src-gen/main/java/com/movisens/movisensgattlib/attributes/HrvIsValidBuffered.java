package com.movisens.movisensgattlib.attributes;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.movisensgattlib.helper.BufferedAttribute;
import com.movisens.smartgattlib.helper.AbstractAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class HrvIsValidBuffered extends AbstractAttribute implements BufferedAttribute
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

	@Override
	public String[] getValueNames()
	{
		String[] names = {"hrvIsValid"};
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
		int numSamples = hrvIsValid.length;
		double[][] data = new double[numSamples][1];
		
		for(int i=0; i<numSamples; i++)
		{
			data[i][0] = hrvIsValid[i] ? 1.0 : 0.0;
		}
		
		return data;
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
			result += "time = " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date((time + (periodLength * i)) * 1000)) + ", " + getHrvIsValid()[i].toString() + " \r\n";
		}
		return result;
	}
}
