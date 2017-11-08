package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class Light extends AbstractReadAttribute
{

	public static final Characteristic CHARACTERISTIC = MovisensCharacteristics.LIGHT;
	
	private Long red;
	private Long green;
	private Long blue;
	private Long clear;
	private Long ir;
	
	public Long getRed()
	{
		return red;
	}
	
	public String getRedUnit()
	{
		return "";
	}
	
	public Long getGreen()
	{
		return green;
	}
	
	public String getGreenUnit()
	{
		return "";
	}
	
	public Long getBlue()
	{
		return blue;
	}
	
	public String getBlueUnit()
	{
		return "";
	}
	
	public Long getClear()
	{
		return clear;
	}
	
	public String getClearUnit()
	{
		return "";
	}
	
	public Long getIr()
	{
		return ir;
	}
	
	public String getIrUnit()
	{
		return "";
	}
	
	public Light(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		red = bb.getUint32();
		green = bb.getUint32();
		blue = bb.getUint32();
		clear = bb.getUint32();
		ir = bb.getUint32();
	}

	@Override
	public Characteristic getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		return "Light: " + "red = " + getRed() + ", " + "green = " + getGreen() + ", " + "blue = " + getBlue() + ", " + "clear = " + getClear() + ", " + "ir = " + getIr();
	}
}
