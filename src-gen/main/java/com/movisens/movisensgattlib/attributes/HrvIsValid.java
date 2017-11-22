package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class HrvIsValid extends AbstractReadAttribute
{

	public static final Characteristic CHARACTERISTIC = MovisensCharacteristics.HRV_IS_VALID;
	
	private Boolean hrvIsValid;
	
	public Boolean getHrvIsValid()
	{
		return hrvIsValid;
	}
	
	public String getHrvIsValidUnit()
	{
		return "";
	}
	
	public HrvIsValid(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		hrvIsValid = bb.getBoolean();
	}

	@Override
	public Characteristic getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		return getHrvIsValid().toString();
	}
}
