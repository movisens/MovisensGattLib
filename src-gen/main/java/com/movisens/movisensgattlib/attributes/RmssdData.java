package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.helper.AbstractData;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;

public class RmssdData extends AbstractData
{
	private Short rmssd;
	
	public Short getRmssd()
	{
		return rmssd;
	}
	
    public RmssdData(long localTime, long sampleTime, int periodlength, BufferedCharacteristic<RmssdBuffered, RmssdData> characteristic, Short rmssd)
    {
        super(localTime, sampleTime, periodlength, characteristic);
		this.rmssd = rmssd;
    }
	
}
