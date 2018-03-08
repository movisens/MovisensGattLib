package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.helper.AbstractData;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;
import com.movisens.movisensgattlib.MovisensCharacteristics;

public class HrvIsValidData extends AbstractData
{
	private Boolean hrvIsValid;
	
	public Boolean getHrvIsValid()
	{
		return hrvIsValid;
	}
	
    public HrvIsValidData(long localTime, long sampleTime, int periodlength, Boolean hrvIsValid)
    {
        super(localTime, sampleTime, periodlength);
		this.hrvIsValid = hrvIsValid;
    }
	
    @Override
    public BufferedCharacteristic<?, ?> getCharacteristic()
    {
        return MovisensCharacteristics.HRV_IS_VALID_BUFFERED;
    }
}
