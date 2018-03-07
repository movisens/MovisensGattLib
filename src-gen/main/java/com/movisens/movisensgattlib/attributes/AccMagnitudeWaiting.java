package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class AccMagnitudeWaiting extends AbstractReadAttribute
{

	public static final Characteristic<AccMagnitudeWaiting> CHARACTERISTIC = MovisensCharacteristics.ACC_MAGNITUDE_WAITING;
	
	private Long samplesWaiting;
	
	public Long getSamplesWaiting()
	{
		return samplesWaiting;
	}
	
	public String getSamplesWaitingUnit()
	{
		return "";
	}
	
	public AccMagnitudeWaiting(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		samplesWaiting = bb.getUint32();
	}

	@Override
	public Characteristic<AccMagnitudeWaiting> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		return getSamplesWaiting().toString();
	}
}
