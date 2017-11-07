package com.movisens.movisensgattlib.attributes;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class Pim extends AbstractReadAttribute
{

	public static final Characteristic CHARACTERISTIC = MovisensCharacteristics.PIM;
	
	public static final int periodLength = 60;
	private long time;
	private Short pim[];
	
	public Short[] getPim()
	{
		return pim;
	}
	
	public String getPimUnit()
	{
		return "";
	}
	
	public Pim(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		
		time = bb.getUint32();
		short numValues = bb.getUint8();
		
		pim = new Short[numValues];
		
		for (int i = 0; i < numValues; i++)
		{
			pim[i] = bb.getInt16();
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
		for(int i=0; i<pim.length; i++)
		{
			result += "Pim: " + "time=" + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date((time + (periodLength * i)) * 1000)) + "pim = " + getPim()[i] + " " + getPimUnit() + "\r\n";
		}
		return result;
	}
}
