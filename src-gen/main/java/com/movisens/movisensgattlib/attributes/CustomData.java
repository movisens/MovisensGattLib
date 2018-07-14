package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadWriteAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class CustomData extends AbstractReadWriteAttribute
{

	public static final Characteristic<CustomData> CHARACTERISTIC = MovisensCharacteristics.CUSTOM_DATA;
	
	
	public CustomData(byte[] data)
	{
		this.data = data;
	}

	@Override
	public Characteristic<CustomData> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

}
