package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.helper.AbstractData;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;

public class StepsData extends AbstractData
{
	private Integer steps;
	
	public Integer getSteps()
	{
		return steps;
	}
	
    public StepsData(long localTime, long sampleTime, int periodlength, BufferedCharacteristic<StepsBuffered, StepsData> characteristic, Integer steps)
    {
        super(localTime, sampleTime, periodlength, characteristic);
		this.steps = steps;
    }
	
}
