package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.helper.AbstractData;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;

public class PimData extends AbstractData
{
	private Short pim;
	
	public Short getPim()
	{
		return pim;
	}
	
    public PimData(long localTime, long sampleTime, int periodlength, BufferedCharacteristic<PimBuffered, PimData> characteristic, Short pim)
    {
        super(localTime, sampleTime, periodlength, characteristic);
		this.pim = pim;
    }
	
}
