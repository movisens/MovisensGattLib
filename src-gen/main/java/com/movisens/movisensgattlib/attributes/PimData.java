package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.helper.AbstractData;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;
import com.movisens.movisensgattlib.MovisensCharacteristics;

public class PimData extends AbstractData
{
	private Short pim;
	
	public Short getPim()
	{
		return pim;
	}
	
    public PimData(long localTime, long sampleTime, int periodlength, Short pim)
    {
        super(localTime, sampleTime, periodlength);
		this.pim = pim;
    }
	
    @Override
    public BufferedCharacteristic<?, ?> getCharacteristic()
    {
        return MovisensCharacteristics.PIM_BUFFERED;
    }
}
