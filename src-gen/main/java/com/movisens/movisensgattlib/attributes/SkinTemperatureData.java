package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.helper.AbstractData;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;
import com.movisens.movisensgattlib.MovisensCharacteristics;

public class SkinTemperatureData extends AbstractData
{
	private Double skinTemperature;
	
	public Double getSkinTemperature()
	{
		return skinTemperature;
	}
	
    public SkinTemperatureData(long localTime, long sampleTime, int periodlength, Double skinTemperature)
    {
        super(localTime, sampleTime, periodlength);
		this.skinTemperature = skinTemperature;
    }
	
    @Override
    public BufferedCharacteristic<?, ?> getCharacteristic()
    {
        return MovisensCharacteristics.SKIN_TEMPERATURE_BUFFERED;
    }
}
