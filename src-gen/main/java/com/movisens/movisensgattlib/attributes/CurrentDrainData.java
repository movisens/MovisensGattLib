package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.helper.AbstractData;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;

public class CurrentDrainData extends AbstractData
{
	private Double currentDrain;
	
	public Double getCurrentDrain()
	{
		return currentDrain;
	}
	
    public CurrentDrainData(long localTime, long sampleTime, int periodlength, BufferedCharacteristic<CurrentDrainBuffered, CurrentDrainData> characteristic, Double currentDrain)
    {
        super(localTime, sampleTime, periodlength, characteristic);
		this.currentDrain = currentDrain;
    }
	
}
