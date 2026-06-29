package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.helper.AbstractData;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;
import com.movisens.movisensgattlib.MovisensCharacteristics;

public class BatteryVoltageData extends AbstractData
{
	private Double voltage;
	
	public Double getVoltage()
	{
		return voltage;
	}
	
    public BatteryVoltageData(long localTime, long sampleTime, int periodLength, Double voltage)
    {
        super(localTime, sampleTime, periodLength);
		this.voltage = voltage;
    }
	
    @Override
    public BufferedCharacteristic<?, ?> getCharacteristic()
    {
        return MovisensCharacteristics.BATTERY_VOLTAGE_BUFFERED;
    }
}
