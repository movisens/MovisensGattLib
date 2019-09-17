package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadWriteAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class TimeZoneOffset extends AbstractReadWriteAttribute
{

	public static final Characteristic<TimeZoneOffset> CHARACTERISTIC = MovisensCharacteristics.TIME_ZONE_OFFSET;
	
	private Integer zoneOffset;
	
	public Integer getZoneOffset()
	{
		return zoneOffset;
	}
	
	public String getZoneOffsetUnit()
	{
		return "";
	}
	
	public TimeZoneOffset(Integer zoneOffset)
	{
		this.zoneOffset = zoneOffset;
		GattByteBuffer bb = GattByteBuffer.allocate(4);
		bb.putInt32(zoneOffset);
		this.data = bb.array();
	}

	public TimeZoneOffset(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		zoneOffset = bb.getInt32();
	}

	@Override
	public Characteristic<TimeZoneOffset> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		return getZoneOffset().toString();
	}
}
