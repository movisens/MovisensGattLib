package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class ColorTemperatureWaiting extends AbstractReadAttribute
{

	public static final Characteristic CHARACTERISTIC = MovisensCharacteristics.COLOR_TEMPERATURE_WAITING;
	
	private Long samplesWaiting;
	
	public Long getSamplesWaiting()
	{
		return samplesWaiting;
	}
	
	public String getSamplesWaitingUnit()
	{
		return "";
	}
	
	public ColorTemperatureWaiting(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		samplesWaiting = bb.getUint32();
	}

	@Override
	public Characteristic getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		return "Color Temperature Waiting: " + "samplesWaiting = " + getSamplesWaiting() + " " + getSamplesWaitingUnit();
	}
}