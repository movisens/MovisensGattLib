package com.movisens.movisensgattlib.attributes;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class IlluminationBuffered extends AbstractReadAttribute
{

	public static final Characteristic CHARACTERISTIC = MovisensCharacteristics.ILLUMINATION_BUFFERED;
	
	public static final int periodLength = 60;
	private long time;
	private Long illumination[];
	
	public Long[] getIllumination()
	{
		return illumination;
	}
	
	public String getIlluminationUnit()
	{
		return "";
	}
	
	public IlluminationBuffered(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		
		time = bb.getUint32();
		short numValues = bb.getUint8();
		
		illumination = new Long[numValues];
		
		for (int i = 0; i < numValues; i++)
		{
			illumination[i] = bb.getUint32();
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
		for(int i=0; i<illumination.length; i++)
		{
			result += "Illumination Buffered: " + "time = " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date((time + (periodLength * i)) * 1000)) + ", " + "illumination = " + getIllumination()[i] + "\r\n";
		}
		return result;
	}
}
