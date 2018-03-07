package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.helper.AbstractData;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;

public class IlluminationData extends AbstractData
{
	private Long illumination;
	
	public Long getIllumination()
	{
		return illumination;
	}
	
    public IlluminationData(long localTime, long sampleTime, int periodlength, BufferedCharacteristic<IlluminationBuffered, IlluminationData> characteristic, Long illumination)
    {
        super(localTime, sampleTime, periodlength, characteristic);
		this.illumination = illumination;
    }
	
}
