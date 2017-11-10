package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class Inclination extends AbstractReadAttribute
{

	public static final Characteristic CHARACTERISTIC = MovisensCharacteristics.INCLINATION;
	
	private Short x;
	private Short y;
	private Short z;
	
	public Short getX()
	{
		return x;
	}
	
	public String getXUnit()
	{
		return "";
	}
	
	public Short getY()
	{
		return y;
	}
	
	public String getYUnit()
	{
		return "";
	}
	
	public Short getZ()
	{
		return z;
	}
	
	public String getZUnit()
	{
		return "";
	}
	
	public Inclination(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		x = bb.getUint8();
		y = bb.getUint8();
		z = bb.getUint8();
	}

	@Override
	public Characteristic getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		return "Inclination: " + "x = " + getX() + ", " + "y = " + getY() + ", " + "z = " + getZ();
	}
}
