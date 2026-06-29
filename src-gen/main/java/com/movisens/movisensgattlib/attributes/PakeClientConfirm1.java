package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractWriteAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;
import com.movisens.smartgattlib.helper.PlainTextAttribute;

/**
 * First part of the client key-confirmation MAC HMAC-SHA256 (bytes 0..19).
 * This triggers a command_result notification.
 * Possible results:
 * - OK: the fragment was accepted.
 * - INVALID_PAKE_STATE: no active PAKE session, failed PAKE state or invalid length.
 * If pake_client_confirm_2 was already written and this write completes the
 * confirmation, the final result can also be:
 * - INVALID_POINT: PAKE key derivation failed before confirmation verification.
 * - WRONG_CODE: the client confirmation does not match the PAKE secret.
 * - KEY_CONFIRMATION_FAILED: the sensor could not compute its confirmation or session key.
 */
public class PakeClientConfirm1 extends AbstractWriteAttribute implements PlainTextAttribute
{

	public static final Characteristic<PakeClientConfirm1> CHARACTERISTIC = MovisensCharacteristics.PAKE_CLIENT_CONFIRM_1;
	
	
	public PakeClientConfirm1(byte[] data)
	{
		if (data.length != 20)
		{
			throw new IllegalArgumentException("PakeClientConfirm1 expects 20 bytes but got " + data.length);
		}
		this.data = data;
	}

	@Override
	public Characteristic<PakeClientConfirm1> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

}
