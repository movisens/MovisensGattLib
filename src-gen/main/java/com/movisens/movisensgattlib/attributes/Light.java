package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class Light extends AbstractReadAttribute
{

	public static final Characteristic CHARACTERISTIC = MovisensCharacteristics.LIGHT;
	
	private Long clear;
	private Long ir;
	
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
		return "clear = " + getClear() + ", " + "ir = " + getIr();
	}
}
