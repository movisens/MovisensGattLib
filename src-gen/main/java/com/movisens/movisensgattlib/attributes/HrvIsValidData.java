package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.helper.AbstractData;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;

public class HrvIsValidData extends AbstractData
{
	private Boolean hrvIsValid;
	
	public Boolean getHrvIsValid()
	{
		return hrvIsValid;
	}
	
    public HrvIsValidData(long localTime, long sampleTime, int periodlength, BufferedCharacteristic<HrvIsValidBuffered, HrvIsValidData> characteristic, Boolean hrvIsValid)
    {
        super(localTime, sampleTime, periodlength, characteristic);
		this.hrvIsValid = hrvIsValid;
    }
	
}
