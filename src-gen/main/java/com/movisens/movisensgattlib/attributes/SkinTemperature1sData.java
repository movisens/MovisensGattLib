package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.helper.AbstractData;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;
import com.movisens.movisensgattlib.MovisensCharacteristics;

public class SkinTemperature1sData extends AbstractData
{
	private Double temperature;
	
	public Double getTemperature()
	{
		return temperature;
	}
	
    public SkinTemperature1sData(long localTime, long sampleTime, int periodLength, Double temperature)
    {
        super(localTime, sampleTime, periodLength);
		this.temperature = temperature;
    }
	
    @Override
    public BufferedCharacteristic<?, ?> getCharacteristic()
    {
        return MovisensCharacteristics.SKIN_TEMPERATURE_1S_BUFFERED;
    }
}
