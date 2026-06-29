package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractWriteAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;
import com.movisens.smartgattlib.helper.PlainTextAttribute;

/**
 * Disables encryption.
 * This triggers a command_result notification.
 * Possible results:
 * - OK: encryption was disabled and the PAKE session was cleared.
 */
public class DisableEncryption extends AbstractWriteAttribute implements PlainTextAttribute
{

	public static final Characteristic<DisableEncryption> CHARACTERISTIC = MovisensCharacteristics.DISABLE_ENCRYPTION;
	
	
	public DisableEncryption()
	{
		GattByteBuffer bb = GattByteBuffer.allocate(1);
		bb.putInt8((byte) 0);
		this.data = bb.array();
	}

	@Override
	public Characteristic<DisableEncryption> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		return "DISABLE_ENCRYPTION";
	}
}
