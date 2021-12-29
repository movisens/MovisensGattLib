package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;
import com.movisens.smartgattlib.helper.PlainTextAttribute;

public class EncryptionEnabled extends AbstractReadAttribute implements PlainTextAttribute
{

	public static final Characteristic<EncryptionEnabled> CHARACTERISTIC = MovisensCharacteristics.ENCRYPTION_ENABLED;
	
	private Boolean value;
	
	public Boolean getValue()
	{
		return value;
	}
	
	public String getValueUnit()
	{
		return "";
	}
	
	public EncryptionEnabled(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		value = bb.getBoolean();
	}

	@Override
	public Characteristic<EncryptionEnabled> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		return getValue().toString();
	}
}
