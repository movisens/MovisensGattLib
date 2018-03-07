package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.helper.AbstractData;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;

public class HrMeanData extends AbstractData
{
	private Short hrMean;
	
	public Short getHrMean()
	{
		return hrMean;
	}
	
    public HrMeanData(long localTime, long sampleTime, int periodlength, BufferedCharacteristic<HrMeanBuffered, HrMeanData> characteristic, Short hrMean)
    {
        super(localTime, sampleTime, periodlength, characteristic);
		this.hrMean = hrMean;
    }
	
}
