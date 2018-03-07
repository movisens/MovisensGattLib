package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.helper.AbstractData;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;

public class ColorTemperatureData extends AbstractData
{
	private Long colorTemperature;
	
	public Long getColorTemperature()
	{
		return colorTemperature;
	}
	
    public ColorTemperatureData(long localTime, long sampleTime, int periodlength, BufferedCharacteristic<ColorTemperatureBuffered, ColorTemperatureData> characteristic, Long colorTemperature)
    {
        super(localTime, sampleTime, periodlength, characteristic);
		this.colorTemperature = colorTemperature;
    }
	
}
