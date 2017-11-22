package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class MovementAcceleration extends AbstractReadAttribute
{

	public static final Characteristic CHARACTERISTIC = MovisensCharacteristics.MOVEMENT_ACCELERATION;
	
	private Double movementAcceleration;
	
	public Double getMovementAcceleration()
	{
		return movementAcceleration;
	}
	
	public String getMovementAccelerationUnit()
	{
		return "g";
	}
	
	public MovementAcceleration(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		movementAcceleration = ((double)bb.getInt16()) * 0.00390625;
	}

	@Override
	public Characteristic getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		return getMovementAcceleration().toString() + getMovementAccelerationUnit();
	}
}
