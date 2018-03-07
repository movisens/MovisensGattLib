package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.helper.AbstractData;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;

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
	
    public LightData(long localTime, long sampleTime, int periodlength, BufferedCharacteristic<LightBuffered, LightData> characteristic, Long clear, Long ir)
    {
        super(localTime, sampleTime, periodlength, characteristic);
		this.clear = clear;
		this.ir = ir;
    }
	
}
