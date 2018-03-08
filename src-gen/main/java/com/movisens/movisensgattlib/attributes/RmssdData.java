package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.helper.AbstractData;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;
import com.movisens.movisensgattlib.MovisensCharacteristics;

public class RmssdData extends AbstractData
{
	private Short rmssd;
	
	public Short getRmssd()
	{
		return rmssd;
	}
	
    public RmssdData(long localTime, long sampleTime, int periodlength, Short rmssd)
    {
        super(localTime, sampleTime, periodlength);
		this.rmssd = rmssd;
    }
	
    @Override
    public BufferedCharacteristic<?, ?> getCharacteristic()
    {
        return MovisensCharacteristics.RMSSD_BUFFERED;
    }
}
