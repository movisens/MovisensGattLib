package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.helper.AbstractData;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;

public class ChargingData extends AbstractData
{
	private Boolean charging;
	
	public Boolean getCharging()
	{
		return charging;
	}
	
    public ChargingData(long localTime, long sampleTime, int periodlength, BufferedCharacteristic<ChargingBuffered, ChargingData> characteristic, Boolean charging)
    {
        super(localTime, sampleTime, periodlength, characteristic);
		this.charging = charging;
    }
	
}
