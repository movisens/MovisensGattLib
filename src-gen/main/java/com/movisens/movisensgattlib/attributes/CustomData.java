package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadWriteAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

/**
 * Custom data to store general purpose data. After the next measurement ended, data is
 * set do invalid and is deleted before the next measurement if it is not set again.
 * This triggers a command_result notification.
 * Possible results:
 * - OK: the value was accepted.
 * - ACCESS_DENIED: the write is not allowed in the current security state.
 */
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
