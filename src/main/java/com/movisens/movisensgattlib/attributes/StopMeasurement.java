package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractWriteAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

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
