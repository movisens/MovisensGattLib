package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;
import com.movisens.smartgattlib.helper.PlainTextAttribute;

public class SecurityCapabilities extends AbstractReadAttribute implements PlainTextAttribute
{

	public static final Characteristic<SecurityCapabilities> CHARACTERISTIC = MovisensCharacteristics.SECURITY_CAPABILITIES;
	
	private Byte version;
	
	public Byte getVersion()
	{
		return version;
	}
	
	public String getVersionUnit()
	{
		return "";
	}
	
	public SecurityCapabilities(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		version = bb.getInt8();
	}

	@Override
	public Characteristic<SecurityCapabilities> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		return getVersion().toString();
	}
}
