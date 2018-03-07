package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.helper.AbstractData;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;

public class SkinTemperatureData extends AbstractData
{
	private Double temperature;
	
	public Double getTemperature()
	{
		return temperature;
	}
	
    public SkinTemperatureData(long localTime, long sampleTime, int periodlength, BufferedCharacteristic<SkinTemperatureBuffered, SkinTemperatureData> characteristic, Double temperature)
    {
        super(localTime, sampleTime, periodlength, characteristic);
		this.temperature = temperature;
    }
	
}
