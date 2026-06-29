package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractWriteAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;
import com.movisens.smartgattlib.helper.PlainTextAttribute;

/**
 * Explicitly starts a new PAKE session. On an unsealed sensor this generates a fresh colour code
 * and starts the LED blinker; on a sealed sensor it prepares the password-based SPAKE2 session
 * without blinking. This must be written before pake_client_share_1/_2.
 * This triggers a command_result notification.
 * Possible results:
 * - OK: a new PAKE session was started.
 * - UNEXPECTED_EXCEPTION: PAKE initialization failed.
 * - PAKE_RATE_LIMITED_60_MIN: PAKE is locked for the 60 minute tier.
 * - PAKE_RATE_LIMITED_24_H: PAKE is locked for the 24 hour tier.
 */
public class PakeStart extends AbstractWriteAttribute implements PlainTextAttribute
{

	public static final Characteristic<PakeStart> CHARACTERISTIC = MovisensCharacteristics.PAKE_START;
	
	
	public PakeStart()
	{
		GattByteBuffer bb = GattByteBuffer.allocate(1);
		bb.putInt8((byte) 0);
		this.data = bb.array();
	}

	@Override
	public Characteristic<PakeStart> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		return "PAKE_START";
	}
}
