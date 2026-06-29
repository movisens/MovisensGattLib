package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.helper.AbstractData;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;
import com.movisens.movisensgattlib.MovisensCharacteristics;

public class BodyPositionData extends AbstractData
{
	private EnumBodyPosition bodyPosition;
	
	public EnumBodyPosition getBodyPosition()
	{
		return bodyPosition;
	}
	
    public BodyPositionData(long localTime, long sampleTime, int periodLength, EnumBodyPosition bodyPosition)
    {
        super(localTime, sampleTime, periodLength);
		this.bodyPosition = bodyPosition;
    }
	
    @Override
    public BufferedCharacteristic<?, ?> getCharacteristic()
    {
        return MovisensCharacteristics.BODY_POSITION_BUFFERED;
    }
}
