package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class AccMagnitude extends AbstractReadAttribute
{

	public static final Characteristic CHARACTERISTIC = MovisensCharacteristics.ACC_MAGNITUDE;
	
	private Short accMagnitude;
	
	public Short getAccMagnitude()
	{
		return accMagnitude;
	}
	
	public String getAccMagnitudeUnit()
	{
		return "";
	}
	
	public AccMagnitude(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		accMagnitude = bb.getInt16();
	}

	@Override
	public Characteristic getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		return "Acc Magnitude: " + "accMagnitude = " + getAccMagnitude() + " " + getAccMagnitudeUnit();
	}
}
