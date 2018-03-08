package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.helper.AbstractData;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;
import com.movisens.movisensgattlib.MovisensCharacteristics;

public class StepsData extends AbstractData
{
	private Integer steps;
	
	public Integer getSteps()
	{
		return steps;
	}
	
    public StepsData(long localTime, long sampleTime, int periodlength, Integer steps)
    {
        super(localTime, sampleTime, periodlength);
		this.steps = steps;
    }
	
    @Override
    public BufferedCharacteristic<?, ?> getCharacteristic()
    {
        return MovisensCharacteristics.STEPS_BUFFERED;
    }
}
