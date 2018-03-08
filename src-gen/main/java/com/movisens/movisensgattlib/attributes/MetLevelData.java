package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.helper.AbstractData;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;
import com.movisens.movisensgattlib.MovisensCharacteristics;

public class MetLevelData extends AbstractData
{
	private Short sedentary;
	private Short light;
	private Short moderate;
	private Short vigorous;
	
	public Short getSedentary()
	{
		return sedentary;
	}
	public Short getLight()
	{
		return light;
	}
	public Short getModerate()
	{
		return moderate;
	}
	public Short getVigorous()
	{
		return vigorous;
	}
	
    public MetLevelData(long localTime, long sampleTime, int periodlength, Short sedentary, Short light, Short moderate, Short vigorous)
    {
        super(localTime, sampleTime, periodlength);
		this.sedentary = sedentary;
		this.light = light;
		this.moderate = moderate;
		this.vigorous = vigorous;
    }
	
    @Override
    public BufferedCharacteristic<?, ?> getCharacteristic()
    {
        return MovisensCharacteristics.MET_LEVEL_BUFFERED;
    }
}
