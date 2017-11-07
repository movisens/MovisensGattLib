package com.movisens.movisensgattlib.attributes;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class Zcm extends AbstractReadAttribute
{

	public static final Characteristic CHARACTERISTIC = MovisensCharacteristics.ZCM;
	
	public static final int periodLength = 60;
	private long time;
	private Short zcm[];
	
	public Short[] getZcm()
	{
		return zcm;
	}
	
	public String getZcmUnit()
	{
		return "";
	}
	
	public Zcm(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		
		time = bb.getUint32();
		short numValues = bb.getUint8();
		
		zcm = new Short[numValues];
		
		for (int i = 0; i < numValues; i++)
		{
			zcm[i] = bb.getInt16();
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
		for(int i=0; i<zcm.length; i++)
		{
			result += "Zcm: " + "time=" + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date((time + (periodLength * i)) * 1000)) + "zcm = " + getZcm()[i] + " " + getZcmUnit() + "\r\n";
		}
		return result;
	}
}
