package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.helper.AbstractData;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;
import com.movisens.movisensgattlib.MovisensCharacteristics;

public class SensorTemperatureData extends AbstractData
{
	private Integer temperature;
	
	public Integer getTemperature()
	{
		return temperature;
	}
	
    public SensorTemperatureData(long localTime, long sampleTime, int periodlength, Integer temperature)
    {
        super(localTime, sampleTime, periodlength);
		this.temperature = temperature;
    }
	
    @Override
    public BufferedCharacteristic<?, ?> getCharacteristic()
    {
        return MovisensCharacteristics.SENSOR_TEMPERATURE_BUFFERED;
    }
}
