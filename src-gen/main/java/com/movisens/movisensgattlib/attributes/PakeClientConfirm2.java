package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractWriteAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;
import com.movisens.smartgattlib.helper.PlainTextAttribute;

/**
 * Second part of the client key-confirmation MAC (bytes 20..31).
 * This triggers a command_result notification.
 * Possible results:
 * - OK: the fragment was accepted.
 * - INVALID_PAKE_STATE: no active PAKE session, failed PAKE state or invalid length.
 * If pake_client_confirm_1 was already written and this write completes the
 * confirmation, the final result can also be:
 * - INVALID_POINT: PAKE key derivation failed before confirmation verification.
 * - WRONG_CODE: the client confirmation does not match the PAKE secret.
 * - KEY_CONFIRMATION_FAILED: the sensor could not compute its confirmation or session key.
 */
public class PakeClientConfirm2 extends AbstractWriteAttribute implements PlainTextAttribute
{

	public static final Characteristic<PakeClientConfirm2> CHARACTERISTIC = MovisensCharacteristics.PAKE_CLIENT_CONFIRM_2;
	
	
	public PakeClientConfirm2(byte[] data)
	{
		if (data.length != 12)
		{
			throw new IllegalArgumentException("PakeClientConfirm2 expects 12 bytes but got " + data.length);
		}
		this.data = data;
	}

	@Override
	public Characteristic<PakeClientConfirm2> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

}
