package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.helper.AbstractData;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;
import com.movisens.movisensgattlib.MovisensCharacteristics;

public class ChargingData extends AbstractData
{
	private Boolean charging;
	
	public Boolean getCharging()
	{
		return charging;
	}
	
    public ChargingData(long localTime, long sampleTime, int periodlength, Boolean charging)
    {
        super(localTime, sampleTime, periodlength);
		this.charging = charging;
    }
	
    @Override
    public BufferedCharacteristic<?, ?> getCharacteristic()
    {
        return MovisensCharacteristics.CHARGING_BUFFERED;
    }
}
