package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.helper.AbstractData;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;

public class InclinationData extends AbstractData
{
	private Short x;
	private Short y;
	private Short z;
	
	public Short getX()
	{
		return x;
	}
	public Short getY()
	{
		return y;
	}
	public Short getZ()
	{
		return z;
	}
	
    public InclinationData(long localTime, long sampleTime, int periodlength, BufferedCharacteristic<InclinationBuffered, InclinationData> characteristic, Short x, Short y, Short z)
    {
        super(localTime, sampleTime, periodlength, characteristic);
		this.x = x;
		this.y = y;
		this.z = z;
    }
	
}
