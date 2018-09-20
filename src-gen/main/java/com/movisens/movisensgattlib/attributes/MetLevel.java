package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class MetLevel extends AbstractReadAttribute
{

	public static final Characteristic<MetLevel> CHARACTERISTIC = MovisensCharacteristics.MET_LEVEL;
	
	private Double sedentary;
	private Double light;
	private Double moderate;
	private Double vigorous;
	
	public Double getSedentary()
	{
		return sedentary;
	}
	
	public String getSedentaryUnit()
	{
		return "s";
	}
	
	public Double getLight()
	{
		return light;
	}
	
	public String getLightUnit()
	{
		return "s";
	}
	
	public Double getModerate()
	{
		return moderate;
	}
	
	public String getModerateUnit()
	{
		return "s";
	}
	
	public Double getVigorous()
	{
		return vigorous;
	}
	
	public String getVigorousUnit()
	{
		return "s";
	}
	
	public MetLevel(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		sedentary = new Double(bb.getUint8());
		light = new Double(bb.getUint8());
		moderate = new Double(bb.getUint8());
		vigorous = new Double(bb.getUint8());
	}

	@Override
	public Characteristic<MetLevel> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		return "sedentary = " + getSedentary() + getSedentaryUnit() + ", " + "light = " + getLight() + getLightUnit() + ", " + "moderate = " + getModerate() + getModerateUnit() + ", " + "vigorous = " + getVigorous() + getVigorousUnit();
	}
}
