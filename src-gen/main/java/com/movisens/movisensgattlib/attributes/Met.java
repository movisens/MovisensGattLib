package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

/**
 * Current value of met (metabolic equivalent of task).
 *
 * Needs characteristics age, gender, weight, height and sensor_location to be set in user_data service!
 */
public class Met extends AbstractReadAttribute
{

	public static final Characteristic<Met> CHARACTERISTIC = MovisensCharacteristics.MET;
	
	private Double met;
	
	public Double getMet()
	{
		return met;
	}
	
	public String getMetUnit()
	{
		return "";
	}
	
	public Met(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		met = ((double)bb.getUint16()) * 0.00390625;
	}

	@Override
	public Characteristic<Met> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		return getMet().toString();
	}
}
