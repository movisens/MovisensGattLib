package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class MeasurementStartTime extends AbstractReadAttribute
{

	public static final Characteristic<MeasurementStartTime> CHARACTERISTIC = MovisensCharacteristics.MEASUREMENT_START_TIME;
	
	private java.util.Date time;
	
	public java.util.Date getTime()
	{
		return time;
	}
	
	public String getTimeUnit()
	{
		return "";
	}
	
	public MeasurementStartTime(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		time = bb.getMstime();
	}

	@Override
	public Characteristic<MeasurementStartTime> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		return getTime().toString();
	}
}
