package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.helper.AbstractData;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;
import com.movisens.movisensgattlib.MovisensCharacteristics;

public class CurrentDrainData extends AbstractData
{
	private Double currentDrain;
	
	public Double getCurrentDrain()
	{
		return currentDrain;
	}
	
    public CurrentDrainData(long localTime, long sampleTime, int periodlength, Double currentDrain)
    {
        super(localTime, sampleTime, periodlength);
		this.currentDrain = currentDrain;
    }
	
    @Override
    public BufferedCharacteristic<?, ?> getCharacteristic()
    {
        return MovisensCharacteristics.CURRENT_DRAIN_BUFFERED;
    }
}
