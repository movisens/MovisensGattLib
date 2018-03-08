package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.helper.AbstractData;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;
import com.movisens.movisensgattlib.MovisensCharacteristics;

public class LightData extends AbstractData
{
	private Long clear;
	private Long ir;
	
	public Long getClear()
	{
		return clear;
	}
	public Long getIr()
	{
		return ir;
	}
	
    public LightData(long localTime, long sampleTime, int periodlength, Long clear, Long ir)
    {
        super(localTime, sampleTime, periodlength);
		this.clear = clear;
		this.ir = ir;
    }
	
    @Override
    public BufferedCharacteristic<?, ?> getCharacteristic()
    {
        return MovisensCharacteristics.LIGHT_BUFFERED;
    }
}
