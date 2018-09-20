package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.helper.AbstractData;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;
import com.movisens.movisensgattlib.MovisensCharacteristics;

public class InclinationData extends AbstractData
{
	private Double x;
	private Double y;
	private Double z;
	
	public Double getX()
	{
		return x;
	}
	public Double getY()
	{
		return y;
	}
	public Double getZ()
	{
		return z;
	}
	
    public InclinationData(long localTime, long sampleTime, int periodlength, Double x, Double y, Double z)
    {
        super(localTime, sampleTime, periodlength);
		this.x = x;
		this.y = y;
		this.z = z;
    }
	
    @Override
    public BufferedCharacteristic<?, ?> getCharacteristic()
    {
        return MovisensCharacteristics.INCLINATION_BUFFERED;
    }
}
