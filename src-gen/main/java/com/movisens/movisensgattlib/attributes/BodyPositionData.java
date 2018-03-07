package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.helper.AbstractData;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;

public class BodyPositionData extends AbstractData
{
	private EnumBodyPosition bodyPosition;
	
	public EnumBodyPosition getBodyPosition()
	{
		return bodyPosition;
	}
	
    public BodyPositionData(long localTime, long sampleTime, int periodlength, BufferedCharacteristic<BodyPositionBuffered, BodyPositionData> characteristic, EnumBodyPosition bodyPosition)
    {
        super(localTime, sampleTime, periodlength, characteristic);
		this.bodyPosition = bodyPosition;
    }
	
}
