package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

/**
 * the position of the users body. Needs characteristic sensor_location to be set in user_data service!
 */
public class BodyPosition extends AbstractReadAttribute
{

	public static final Characteristic<BodyPosition> CHARACTERISTIC = MovisensCharacteristics.BODY_POSITION;
	
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
	public Characteristic<BodyPosition> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		return getBodyPosition().toString();
	}
}
