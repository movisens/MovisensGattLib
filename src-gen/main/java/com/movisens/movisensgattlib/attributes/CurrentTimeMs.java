package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadWriteAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

/**
 * A number representing the milliseconds elapsed between 1 January 1970 00:00:00 UTC and the given date in UTC. Must be set before the Measurement is Enabed.
 * This triggers a command_result notification.
 * Possible results:
 * - OK: the value was accepted.
 * - ACCESS_DENIED: the write is not allowed in the current security state.
 */
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
