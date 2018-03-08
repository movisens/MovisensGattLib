package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.helper.AbstractData;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;
import com.movisens.movisensgattlib.MovisensCharacteristics;

public class TapMarkerData extends AbstractData
{
	private Long tapMarker;
	
	public Long getTapMarker()
	{
		return tapMarker;
	}
	
    public TapMarkerData(long localTime, long sampleTime, int periodlength, Long tapMarker)
    {
        super(localTime, sampleTime, periodlength);
		this.tapMarker = tapMarker;
    }
	
    @Override
    public BufferedCharacteristic<?, ?> getCharacteristic()
    {
        return MovisensCharacteristics.TAP_MARKER_BUFFERED;
    }
}
