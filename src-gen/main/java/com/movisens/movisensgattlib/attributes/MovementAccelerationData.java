package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.helper.AbstractData;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;
import com.movisens.movisensgattlib.MovisensCharacteristics;

public class MovementAccelerationData extends AbstractData
{
	private Double movementAcceleration;
	
	public Double getMovementAcceleration()
	{
		return movementAcceleration;
	}
	
    public MovementAccelerationData(long localTime, long sampleTime, int periodlength, Double movementAcceleration)
    {
        super(localTime, sampleTime, periodlength);
		this.movementAcceleration = movementAcceleration;
    }
	
    @Override
    public BufferedCharacteristic<?, ?> getCharacteristic()
    {
        return MovisensCharacteristics.MOVEMENT_ACCELERATION_BUFFERED;
    }
}
