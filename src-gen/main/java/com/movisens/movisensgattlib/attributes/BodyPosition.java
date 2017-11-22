package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class BodyPosition extends AbstractReadAttribute
{

	public static final Characteristic CHARACTERISTIC = MovisensCharacteristics.BODY_POSITION;
	
	private EnumBodyPosition bodyPosition;
	
	public EnumBodyPosition getBodyPosition()
	{
		return bodyPosition;
	}
	
	public String getBodyPositionUnit()
	{
		return "";
	}
	
	public BodyPosition(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		bodyPosition = EnumBodyPosition.getByValue(bb.getUint8());
	}

	@Override
	public Characteristic getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		return getBodyPosition().toString();
	}
}
