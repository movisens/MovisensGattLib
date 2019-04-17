package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractWriteAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class StartMeasurement extends AbstractWriteAttribute
{

	public static final Characteristic<StartMeasurement> CHARACTERISTIC = MovisensCharacteristics.START_MEASUREMENT;
	
	private Long duration;
	
	public Long getDuration()
	{
		return duration;
	}
	
	public String getDurationUnit()
	{
		return "";
	}
	
	public StartMeasurement(Long duration)
	{
		this.duration = duration;
		GattByteBuffer bb = GattByteBuffer.allocate(4);
		bb.putUint32(duration);
		this.data = bb.array();
	}

	@Override
	public Characteristic<StartMeasurement> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		return getDuration().toString();
	}
}
