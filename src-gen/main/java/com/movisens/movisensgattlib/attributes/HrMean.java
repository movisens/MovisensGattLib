package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class HrMean extends AbstractReadAttribute
{

	public static final Characteristic CHARACTERISTIC = MovisensCharacteristics.HR_MEAN;
	
	private Short hrMean;
	
	public Short getHrMean()
	{
		return hrMean;
	}
	
	public String getHrMeanUnit()
	{
		return "";
	}
	
	public HrMean(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		hrMean = bb.getInt16();
	}

	@Override
	public Characteristic getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		return "Hr Mean: " + "hrMean = " + getHrMean() + " " + getHrMeanUnit();
	}
}
