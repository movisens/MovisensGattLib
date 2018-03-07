package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.helper.AbstractData;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;

public class MovementAccelerationData extends AbstractData
{
	private Double movementAcceleration;
	
	public Double getMovementAcceleration()
	{
		return movementAcceleration;
	}
	
    public MovementAccelerationData(long localTime, long sampleTime, int periodlength, BufferedCharacteristic<MovementAccelerationBuffered, MovementAccelerationData> characteristic, Double movementAcceleration)
    {
        super(localTime, sampleTime, periodlength, characteristic);
		this.movementAcceleration = movementAcceleration;
    }
	
}
