package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadWriteAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class TimeZone extends AbstractReadWriteAttribute
{

	public static final Characteristic<TimeZone> CHARACTERISTIC = MovisensCharacteristics.TIME_ZONE;
	
	private java.time.ZoneId timeZone;
	
	public java.time.ZoneId getTimeZone()
	{
		return timeZone;
	}
	
	public String getTimeZoneUnit()
	{
		return "";
	}
	
	public TimeZone(java.time.ZoneId timeZone)
	{
		this.timeZone = timeZone;
		GattByteBuffer bb = GattByteBuffer.allocate(20);
		bb.putTimezone(timeZone);
		this.data = bb.array();
	}

	public TimeZone(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		timeZone = bb.getTimezone();
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
