package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class Inclination extends AbstractReadAttribute
{

	public static final Characteristic<Inclination> CHARACTERISTIC = MovisensCharacteristics.INCLINATION;
	
	private Double x;
	private Double y;
	private Double z;
	
	public Double getX()
	{
		return x;
	}
	
	public String getXUnit()
	{
		return "°";
	}
	
	public Double getY()
	{
		return y;
	}
	
	public String getYUnit()
	{
		return "°";
	}
	
	public Double getZ()
	{
		return z;
	}
	
	public String getZUnit()
	{
		return "°";
	}
	
	public Inclination(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		x = new Double(bb.getUint8());
		y = new Double(bb.getUint8());
		z = new Double(bb.getUint8());
	}

	@Override
	public Characteristic<Inclination> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		return "x = " + getX().toString() + getXUnit() + ", " + "y = " + getY().toString() + getYUnit() + ", " + "z = " + getZ().toString() + getZUnit();
	}
}
