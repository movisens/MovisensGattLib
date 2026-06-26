package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;
import com.movisens.smartgattlib.helper.PlainTextAttribute;

/**
 * Balanced SPAKE2 pairing (RFC 9382, ciphersuite P256-SHA256-HKDF-HMAC).
 * The colour code (or the sealing password) is the SPAKE2 password; the AES session
 * key is derived from the SPAKE2 result.
 * Wire format: EC points are SEC1 compressed (33 bytes), split into a _1 part (20 bytes)
 * and a _2 part (13 bytes). Confirmation MACs are HMAC-SHA256 (32 bytes), split into
 * _1 (20) + _2 (12). The uncompressed point form is used only inside the RFC 9382
 * transcript, never on the wire.
 * Flow: read security_capabilities; write pake_start; write pake_client_share_1/_2; read
 * pake_sensor_share_1/_2; write pake_client_confirm_1/_2; read pake_sensor_confirm_1/_2;
 * on success seal_sensor (unsealed pairing) or authenticated session (sealed access).
 * Read command_result after each write; disable_encryption aborts and clears the state.
 * The writable PAKE characteristics below list their possible command_result values.
 * This characteristic returns the supported security protocol version(s) and PAKE
 * suite(s); read it before pairing.
 */
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
