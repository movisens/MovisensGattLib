package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.helper.AbstractData;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;

public class SkinTemperature1sData extends AbstractData
{
	private Double temperature;
	
	public Double getTemperature()
	{
		return temperature;
	}
	
    public SkinTemperature1sData(long localTime, long sampleTime, int periodlength, BufferedCharacteristic<SkinTemperature1sBuffered, SkinTemperature1sData> characteristic, Double temperature)
    {
        super(localTime, sampleTime, periodlength, characteristic);
		this.temperature = temperature;
    }
	
}
