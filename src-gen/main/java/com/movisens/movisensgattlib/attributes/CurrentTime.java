package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadWriteAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class CurrentTime extends AbstractReadWriteAttribute
{

	public static final Characteristic CHARACTERISTIC = MovisensCharacteristics.CURRENT_TIME;
	
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
	public Characteristic getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		return "Current Time: " + "currentTime = " + getCurrentTime() + " " + getCurrentTimeUnit();
	}
}
