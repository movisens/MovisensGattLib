package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.helper.AbstractData;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;

public class EdaSclMeanData extends AbstractData
{
	private Integer edaSclMean;
	
	public Integer getEdaSclMean()
	{
		return edaSclMean;
	}
	
    public EdaSclMeanData(long localTime, long sampleTime, int periodlength, BufferedCharacteristic<EdaSclMeanBuffered, EdaSclMeanData> characteristic, Integer edaSclMean)
    {
        super(localTime, sampleTime, periodlength, characteristic);
		this.edaSclMean = edaSclMean;
    }
	
}
