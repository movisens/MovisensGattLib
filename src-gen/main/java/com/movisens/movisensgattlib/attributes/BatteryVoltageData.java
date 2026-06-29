package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.helper.AbstractData;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;
import com.movisens.movisensgattlib.MovisensCharacteristics;

public class BatteryVoltageData extends AbstractData
{
	private Double level;
	
	public Double getLevel()
	{
		return level;
	}
	
    public BatteryVoltageData(long localTime, long sampleTime, int periodLength, Double level)
    {
        super(localTime, sampleTime, periodLength);
		this.level = level;
    }
	
    @Override
    public BufferedCharacteristic<?, ?> getCharacteristic()
    {
        return MovisensCharacteristics.BATTERY_VOLTAGE_BUFFERED;
    }
}
