package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadWriteAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class TimeZoneId extends AbstractReadWriteAttribute
{

	public static final Characteristic<TimeZoneId> CHARACTERISTIC = MovisensCharacteristics.TIME_ZONE_ID;
	
	private String zoneId;
	
	public String getZoneId()
	{
		return zoneId;
	}
	
	public String getZoneIdUnit()
	{
		return "";
	}
	
	public TimeZoneId(String zoneId)
	{
		this.zoneId = zoneId;
		GattByteBuffer bb = GattByteBuffer.allocate(20);
		bb.putTimezone(zoneId);
		this.data = bb.array();
	}

	public TimeZoneId(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		zoneId = bb.getTimezone();
	}

	@Override
	public Characteristic<TimeZoneId> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		return getZoneId().toString();
	}
}
