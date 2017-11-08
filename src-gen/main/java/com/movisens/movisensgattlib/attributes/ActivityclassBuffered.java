package com.movisens.movisensgattlib.attributes;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class ActivityclassBuffered extends AbstractReadAttribute
{

	public static final Characteristic CHARACTERISTIC = MovisensCharacteristics.ACTIVITYCLASS_BUFFERED;
	
	public static final int periodLength = 60;
	private long time;
	private Short activityClass[];
	
	public Short[] getActivityClass()
	{
		return activityClass;
	}
	
	public String getActivityClassUnit()
	{
		return "";
	}
	
	public ActivityclassBuffered(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		
		time = bb.getUint32();
		short numValues = bb.getUint8();
		
		activityClass = new Short[numValues];
		
		for (int i = 0; i < numValues; i++)
		{
			activityClass[i] = bb.getInt16();
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
		for(int i=0; i<activityClass.length; i++)
		{
			result += "Activityclass Buffered: " + "time = " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date((time + (periodLength * i)) * 1000)) + ", " + "activityClass = " + getActivityClass()[i] + "\r\n";
		}
		return result;
	}
}
