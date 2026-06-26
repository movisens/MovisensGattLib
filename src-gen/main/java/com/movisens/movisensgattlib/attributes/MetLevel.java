package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

/**
 * Number of seconds the users met value was at one of the met levels (sedentary, light, moderate, vigorous)
 *
 * Needs characteristics age, gender, weight, height and sensor_location to be set in user_data service!
 */
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
		sedentary = (double) bb.getUint8();
		light = (double) bb.getUint8();
		moderate = (double) bb.getUint8();
		vigorous = (double) bb.getUint8();
	}

	@Override
	public Characteristic<MetLevel> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		return "sedentary = " + getSedentary().toString() + getSedentaryUnit() + ", " + "light = " + getLight().toString() + getLightUnit() + ", " + "moderate = " + getModerate().toString() + getModerateUnit() + ", " + "vigorous = " + getVigorous().toString() + getVigorousUnit();
	}
}
