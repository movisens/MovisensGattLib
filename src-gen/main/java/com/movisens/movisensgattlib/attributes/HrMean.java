package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class HrMean extends AbstractReadAttribute
{

	public static final Characteristic<HrMean> CHARACTERISTIC = MovisensCharacteristics.HR_MEAN;
	
	private Double hrMean;
	
	public Double getHrMean()
	{
		return hrMean;
	}
	
	public String getHrMeanUnit()
	{
		return "1/min";
	}
	
	public HrMean(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		hrMean = new Double(bb.getInt16());
	}

	@Override
	public Characteristic<HrMean> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		return getHrMean().toString() + getHrMeanUnit();
	}
}
