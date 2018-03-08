package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.helper.AbstractData;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;
import com.movisens.movisensgattlib.MovisensCharacteristics;

public class ColorTemperatureData extends AbstractData
{
	private Long colorTemperature;
	
	public Long getColorTemperature()
	{
		return colorTemperature;
	}
	
    public ColorTemperatureData(long localTime, long sampleTime, int periodlength, Long colorTemperature)
    {
        super(localTime, sampleTime, periodlength);
		this.colorTemperature = colorTemperature;
    }
	
    @Override
    public BufferedCharacteristic<?, ?> getCharacteristic()
    {
        return MovisensCharacteristics.COLOR_TEMPERATURE_BUFFERED;
    }
}
