package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class CurrentDrain extends AbstractReadAttribute
{

	public static final Characteristic CHARACTERISTIC = MovisensCharacteristics.CURRENT_DRAIN;
	
	private Double currentDrain;
	
	public Double getCurrentDrain()
	{
		return currentDrain;
	}
	
	public String getCurrentDrainUnit()
	{
		return "mA";
	}
	
	public CurrentDrain(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		currentDrain = ((double)bb.getInt32()) * 0.01;
	}

	@Override
	public Characteristic getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		return "Current Drain: " + "currentDrain = " + getCurrentDrain() + getCurrentDrainUnit();
	}
}
