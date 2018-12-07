package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class ActivatedBufferedCharacteristics extends AbstractReadAttribute
{

	public static final Characteristic<ActivatedBufferedCharacteristics> CHARACTERISTIC = MovisensCharacteristics.ACTIVATED_BUFFERED_CHARACTERISTICS;
	
	
	public ActivatedBufferedCharacteristics(byte[] data)
	{
		this.data = data;
	}

	@Override
	public Characteristic<ActivatedBufferedCharacteristics> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

}
