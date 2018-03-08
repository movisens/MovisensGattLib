package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.helper.AbstractData;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;
import com.movisens.movisensgattlib.MovisensCharacteristics;

public class EdaCountsData extends AbstractData
{
	private Integer edaCounts;
	
	public Integer getEdaCounts()
	{
		return edaCounts;
	}
	
    public EdaCountsData(long localTime, long sampleTime, int periodlength, Integer edaCounts)
    {
        super(localTime, sampleTime, periodlength);
		this.edaCounts = edaCounts;
    }
	
    @Override
    public BufferedCharacteristic<?, ?> getCharacteristic()
    {
        return MovisensCharacteristics.EDA_COUNTS_BUFFERED;
    }
}
