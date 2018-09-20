package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.helper.AbstractData;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;
import com.movisens.movisensgattlib.MovisensCharacteristics;

public class MetLevelData extends AbstractData
{
	private Double sedentary;
	private Double light;
	private Double moderate;
	private Double vigorous;
	
	public Double getSedentary()
	{
		return sedentary;
	}
	public Double getLight()
	{
		return light;
	}
	public Double getModerate()
	{
		return moderate;
	}
	public Double getVigorous()
	{
		return vigorous;
	}
	
    public MetLevelData(long localTime, long sampleTime, int periodlength, Double sedentary, Double light, Double moderate, Double vigorous)
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
