package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.helper.AbstractData;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;

public class EdaCountsData extends AbstractData
{
	private Integer edaCounts;
	
	public Integer getEdaCounts()
	{
		return edaCounts;
	}
	
    public EdaCountsData(long localTime, long sampleTime, int periodlength, BufferedCharacteristic<EdaCountsBuffered, EdaCountsData> characteristic, Integer edaCounts)
    {
        super(localTime, sampleTime, periodlength, characteristic);
		this.edaCounts = edaCounts;
    }
	
}
