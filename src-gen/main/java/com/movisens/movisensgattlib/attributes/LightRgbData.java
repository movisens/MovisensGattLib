package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.helper.AbstractData;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;

public class LightRgbData extends AbstractData
{
	private Long red;
	private Long green;
	private Long blue;
	
	public Long getRed()
	{
		return red;
	}
	public Long getGreen()
	{
		return green;
	}
	public Long getBlue()
	{
		return blue;
	}
	
    public LightRgbData(long localTime, long sampleTime, int periodlength, BufferedCharacteristic<LightRgbBuffered, LightRgbData> characteristic, Long red, Long green, Long blue)
    {
        super(localTime, sampleTime, periodlength, characteristic);
		this.red = red;
		this.green = green;
		this.blue = blue;
    }
	
}
