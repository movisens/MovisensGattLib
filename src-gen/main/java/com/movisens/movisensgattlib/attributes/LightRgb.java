package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class LightRgb extends AbstractReadAttribute
{

	public static final Characteristic CHARACTERISTIC = MovisensCharacteristics.LIGHT_RGB;
	
	private Long red;
	private Long green;
	private Long blue;
	
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
	
	public LightRgb(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		red = bb.getUint32();
		green = bb.getUint32();
		blue = bb.getUint32();
	}

	@Override
	public Characteristic getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		return "red = " + getRed() + ", " + "green = " + getGreen() + ", " + "blue = " + getBlue();
	}
}
