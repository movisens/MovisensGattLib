package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractWriteAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

/**
 * stops the measurement if any
 * This triggers a command_result notification.
 * Possible results:
 * - OK: measurement stop was accepted.
 * - NOT_STOPPED_MEASUREMENT_OFF: no measurement is active, delayed or paused.
 * - ACCESS_DENIED: the write is not allowed in the current security state.
 */
public class StopMeasurement extends AbstractWriteAttribute
{

	public static final Characteristic<StopMeasurement> CHARACTERISTIC = MovisensCharacteristics.STOP_MEASUREMENT;
	
	public StopMeasurement()
	{
        GattByteBuffer bb = GattByteBuffer.allocate(1);
        bb.putInt8((byte)0);
        this.data = bb.array();
	}
	
	@Override
	public Characteristic<StopMeasurement> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		return "STOP_MEASUREMENT";
	}
}
