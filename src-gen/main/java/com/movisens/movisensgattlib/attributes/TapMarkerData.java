package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.helper.AbstractData;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;

public class TapMarkerData extends AbstractData
{
	private Long tapMarker;
	
	public Long getTapMarker()
	{
		return tapMarker;
	}
	
    public TapMarkerData(long localTime, long sampleTime, int periodlength, BufferedCharacteristic<TapMarkerBuffered, TapMarkerData> characteristic, Long tapMarker)
    {
        super(localTime, sampleTime, periodlength, characteristic);
		this.tapMarker = tapMarker;
    }
	
}
