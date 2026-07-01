package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

/**
 * Ambient light (clear, ir)
 */
public class Light extends AbstractReadAttribute
{

	public static final Characteristic<Light> CHARACTERISTIC = MovisensCharacteristics.LIGHT;
	
	private Integer packetCounter;
	private Long clear;
	private Long ir;
	
	public Integer getPacketCounter()
	{
		return packetCounter;
	}
	
	public String getPacketCounterUnit()
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
		packetCounter = bb.getUint16();
		clear = bb.getUint32();
		ir = bb.getUint32();
	}

	@Override
	public Characteristic<Light> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		return "packetCounter = " + getPacketCounter().toString() + ", " + "clear = " + getClear().toString() + ", " + "ir = " + getIr().toString();
	}
}
