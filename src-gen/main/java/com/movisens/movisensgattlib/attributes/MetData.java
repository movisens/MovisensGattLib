package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.helper.AbstractData;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;
import com.movisens.movisensgattlib.MovisensCharacteristics;

public class MetData extends AbstractData
{
	private Double met;
	
	public Double getMet()
	{
		return met;
	}
	
    public MetData(long localTime, long sampleTime, int periodlength, Double met)
    {
        super(localTime, sampleTime, periodlength);
		this.met = met;
    }
	
    @Override
    public BufferedCharacteristic<?, ?> getCharacteristic()
    {
        return MovisensCharacteristics.MET_BUFFERED;
    }
}
