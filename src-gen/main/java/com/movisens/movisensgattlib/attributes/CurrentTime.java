package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadWriteAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

/**
 * Deprecated! Use current_time_ms instead. A number representing the seconds elapsed between 1 January 1970 00:00:00 UTC and the given date in UTC. Must be set before the Measurement is Enabed.
 * This triggers a command_result notification.
 * Possible results:
 * - OK: the value was accepted.
 * - ACCESS_DENIED: the write is not allowed in the current security state.
 */
public class CurrentTime extends AbstractReadWriteAttribute
{

	public static final Characteristic<CurrentTime> CHARACTERISTIC = MovisensCharacteristics.CURRENT_TIME;
	
	private java.util.Date currentTime;
	
	public java.util.Date getCurrentTime()
	{
		return currentTime;
	}
	
	public String getCurrentTimeUnit()
	{
		return "";
	}
	
	public CurrentTime(java.util.Date currentTime)
	{
		this.currentTime = currentTime;
		GattByteBuffer bb = GattByteBuffer.allocate(4);
		bb.putStime(currentTime);
		this.data = bb.array();
	}

	public CurrentTime(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		currentTime = bb.getStime();
	}

	@Override
	public Characteristic<CurrentTime> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		return getCurrentTime().toString();
	}
}
