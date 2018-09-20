package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.helper.AbstractData;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;
import com.movisens.movisensgattlib.MovisensCharacteristics;

public class HrMeanData extends AbstractData
{
	private Double hrMean;
	
	public Double getHrMean()
	{
		return hrMean;
	}
	
    public HrMeanData(long localTime, long sampleTime, int periodlength, Double hrMean)
    {
        super(localTime, sampleTime, periodlength);
		this.hrMean = hrMean;
    }
	
    @Override
    public BufferedCharacteristic<?, ?> getCharacteristic()
    {
        return MovisensCharacteristics.HR_MEAN_BUFFERED;
    }
}
