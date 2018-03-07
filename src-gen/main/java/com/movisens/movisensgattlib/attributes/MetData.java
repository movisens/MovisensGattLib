package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.helper.AbstractData;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;

public class MetData extends AbstractData
{
	private Double met;
	
	public Double getMet()
	{
		return met;
	}
	
    public MetData(long localTime, long sampleTime, int periodlength, BufferedCharacteristic<MetBuffered, MetData> characteristic, Double met)
    {
        super(localTime, sampleTime, periodlength, characteristic);
		this.met = met;
    }
	
}
