package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadWriteAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class CurrentTimeMs extends AbstractReadWriteAttribute
{

	public static final Characteristic<CurrentTimeMs> CHARACTERISTIC = MovisensCharacteristics.CURRENT_TIME_MS;
	
	private java.util.Date time;
	
	public java.util.Date getTime()
	{
		return time;
	}
	
	public String getTimeUnit()
	{
		return "";
	}
	
	public CurrentTimeMs(java.util.Date time)
	{
		this.time = time;
		GattByteBuffer bb = GattByteBuffer.allocate(8);
		bb.putMstime(time);
		this.data = bb.array();
	}

	public CurrentTimeMs(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		time = bb.getMstime();
	}

	@Override
	public Characteristic<CurrentTimeMs> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		return getTime().toString();
	}
}
