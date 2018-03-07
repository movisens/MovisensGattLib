package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.helper.AbstractData;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;

public class SensorTemperatureData extends AbstractData
{
	private Integer temperature;
	
	public Integer getTemperature()
	{
		return temperature;
	}
	
    public SensorTemperatureData(long localTime, long sampleTime, int periodlength, BufferedCharacteristic<SensorTemperatureBuffered, SensorTemperatureData> characteristic, Integer temperature)
    {
        super(localTime, sampleTime, periodlength, characteristic);
		this.temperature = temperature;
    }
	
}
