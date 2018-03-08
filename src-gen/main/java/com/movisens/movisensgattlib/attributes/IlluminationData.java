package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.helper.AbstractData;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;
import com.movisens.movisensgattlib.MovisensCharacteristics;

public class IlluminationData extends AbstractData
{
	private Long illumination;
	
	public Long getIllumination()
	{
		return illumination;
	}
	
    public IlluminationData(long localTime, long sampleTime, int periodlength, Long illumination)
    {
        super(localTime, sampleTime, periodlength);
		this.illumination = illumination;
    }
	
    @Override
    public BufferedCharacteristic<?, ?> getCharacteristic()
    {
        return MovisensCharacteristics.ILLUMINATION_BUFFERED;
    }
}
