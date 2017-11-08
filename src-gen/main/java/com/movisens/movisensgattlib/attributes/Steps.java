package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class Steps extends AbstractReadAttribute
{

	public static final Characteristic CHARACTERISTIC = MovisensCharacteristics.STEPS;
	
	private Integer steps;
	
	public Integer getSteps()
	{
		return steps;
	}
	
	public String getStepsUnit()
	{
		return "";
	}
	
	public Steps(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		steps = bb.getUint16();
	}

	@Override
	public Characteristic getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		return "Steps: " + "steps = " + getSteps();
	}
}
