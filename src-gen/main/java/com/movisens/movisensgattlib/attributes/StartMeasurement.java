package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractWriteAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

/**
 * send to start a measurement and set duration of measurement in seconds
 * This triggers a command_result notification.
 * Possible results:
 * - OK: measurement start was accepted.
 * - NOT_STARTED_PROBAND_INFO_MISSING: mandatory participant information is missing.
 * - NOT_STARTED_MEASUREMENT_ON: measurement is already active, delayed or paused.
 * - INVALID_CALL_PARAMETERS: duration is greater than INT32_MAX.
 * - EVALUATION_PERIOD_EXPIRED: the evaluation period has expired.
 * - ACCESS_DENIED: the write is not allowed in the current security state.
 */
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
