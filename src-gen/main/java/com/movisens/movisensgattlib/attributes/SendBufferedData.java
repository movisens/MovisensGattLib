package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractWriteAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

/**
 * Writing this characteristic sends out buffered data if available.
 * This triggers a command_result notification.
 * Possible results:
 * - OK: the value was accepted.
 * - ACCESS_DENIED: the write is not allowed in the current security state.
 */
public class SendBufferedData extends AbstractWriteAttribute
{

	public static final Characteristic<SendBufferedData> CHARACTERISTIC = MovisensCharacteristics.SEND_BUFFERED_DATA;
	
	
	public SendBufferedData()
	{
		GattByteBuffer bb = GattByteBuffer.allocate(1);
		bb.putInt8((byte) 0);
		this.data = bb.array();
	}

	@Override
	public Characteristic<SendBufferedData> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		return "SEND_BUFFERED_DATA";
	}
}
