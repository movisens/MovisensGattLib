package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class EdaCounts extends AbstractReadAttribute
{

	public static final Characteristic CHARACTERISTIC = MovisensCharacteristics.EDA_COUNTS;
	
	private Integer edaCounts;
	
	public Integer getEdaCounts()
	{
		return edaCounts;
	}
	
	public String getEdaCountsUnit()
	{
		return "";
	}
	
	public EdaCounts(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		edaCounts = bb.getUint16();
	}

	@Override
	public Characteristic getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		return "Eda Counts: " + "edaCounts = " + getEdaCounts();
	}
}
