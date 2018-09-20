package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.helper.AbstractData;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;
import com.movisens.movisensgattlib.MovisensCharacteristics;

public class EdaSclMeanData extends AbstractData
{
	private Double edaSclMean;
	
	public Double getEdaSclMean()
	{
		return edaSclMean;
	}
	
    public EdaSclMeanData(long localTime, long sampleTime, int periodlength, Double edaSclMean)
    {
        super(localTime, sampleTime, periodlength);
		this.edaSclMean = edaSclMean;
    }
	
    @Override
    public BufferedCharacteristic<?, ?> getCharacteristic()
    {
        return MovisensCharacteristics.EDA_SCL_MEAN_BUFFERED;
    }
}
