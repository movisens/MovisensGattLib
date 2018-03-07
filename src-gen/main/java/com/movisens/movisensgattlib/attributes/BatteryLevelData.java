package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.helper.AbstractData;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;

public class BatteryLevelData extends AbstractData
{
	private Double level;
	
	public Double getLevel()
	{
		return level;
	}
	
    public BatteryLevelData(long localTime, long sampleTime, int periodlength, BufferedCharacteristic<BatteryLevelBuffered, BatteryLevelData> characteristic, Double level)
    {
        super(localTime, sampleTime, periodlength, characteristic);
		this.level = level;
    }
	
}
