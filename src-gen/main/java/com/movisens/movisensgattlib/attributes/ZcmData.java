package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.helper.AbstractData;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;
import com.movisens.movisensgattlib.MovisensCharacteristics;

public class ZcmData extends AbstractData
{
	private Short zcm;
	
	public Short getZcm()
	{
		return zcm;
	}
	
    public ZcmData(long localTime, long sampleTime, int periodlength, Short zcm)
    {
        super(localTime, sampleTime, periodlength);
		this.zcm = zcm;
    }
	
    @Override
    public BufferedCharacteristic<?, ?> getCharacteristic()
    {
        return MovisensCharacteristics.ZCM_BUFFERED;
    }
}
