package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.helper.AbstractData;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;

public class ZcmData extends AbstractData
{
	private Short zcm;
	
	public Short getZcm()
	{
		return zcm;
	}
	
    public ZcmData(long localTime, long sampleTime, int periodlength, BufferedCharacteristic<ZcmBuffered, ZcmData> characteristic, Short zcm)
    {
        super(localTime, sampleTime, periodlength, characteristic);
		this.zcm = zcm;
    }
	
}
