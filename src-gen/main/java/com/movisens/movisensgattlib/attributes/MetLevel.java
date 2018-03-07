package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class MetLevel extends AbstractReadAttribute
{

	public static final Characteristic<MetLevel> CHARACTERISTIC = MovisensCharacteristics.MET_LEVEL;
	
	private Short sedentary;
	private Short light;
	private Short moderate;
	private Short vigorous;
	
	public Short getSedentary()
	{
		return sedentary;
	}
	
	public String getSedentaryUnit()
	{
		return "";
	}
	
	public Short getLight()
	{
		return light;
	}
	
	public String getLightUnit()
	{
		return "";
	}
	
	public Short getModerate()
	{
		return moderate;
	}
	
	public String getModerateUnit()
	{
		return "";
	}
	
	public Short getVigorous()
	{
		return vigorous;
	}
	
	public String getVigorousUnit()
	{
		return "";
	}
	
	public MetLevel(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		sedentary = bb.getUint8();
		light = bb.getUint8();
		moderate = bb.getUint8();
		vigorous = bb.getUint8();
	}

	@Override
	public Characteristic<MetLevel> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		return "sedentary = " + getSedentary() + ", " + "light = " + getLight() + ", " + "moderate = " + getModerate() + ", " + "vigorous = " + getVigorous();
	}
}
