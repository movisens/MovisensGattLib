package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadWriteAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class TimeZone extends AbstractReadWriteAttribute
{

	public static final Characteristic<TimeZone> CHARACTERISTIC = MovisensCharacteristics.TIME_ZONE;
	
	private String timeZone;
	
	public String getTimeZone()
	{
		return timeZone;
	}
	
	public String getTimeZoneUnit()
	{
		return "";
	}
	
	public TimeZone(String timeZone)
	{
		this.timeZone = timeZone;
		GattByteBuffer bb = GattByteBuffer.allocate(0);
		bb.putString(timeZone);
		this.data = bb.array();
	}

	public TimeZone(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		timeZone = bb.getString();
	}

	@Override
	public Characteristic<TimeZone> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		return getTimeZone().toString();
	}
}
