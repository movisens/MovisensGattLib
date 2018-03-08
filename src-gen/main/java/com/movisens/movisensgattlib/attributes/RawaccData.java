package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.helper.AbstractData;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;
import com.movisens.movisensgattlib.MovisensCharacteristics;

public class RawaccData extends AbstractData
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
	
    public RawaccData(long localTime, long sampleTime, int periodlength, Short x, Short y, Short z)
    {
        super(localTime, sampleTime, periodlength);
		this.x = x;
		this.y = y;
		this.z = z;
    }
	
    @Override
    public BufferedCharacteristic<?, ?> getCharacteristic()
    {
        return MovisensCharacteristics.RAWACC_BUFFERED;
    }
}
