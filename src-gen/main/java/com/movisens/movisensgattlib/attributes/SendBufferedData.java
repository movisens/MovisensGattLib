package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractWriteAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

/**
 * If set to 1 and buffered data is available the sensor sends out the data.
 * This triggers a command_result notification.
 * Possible results:
 * - ok: the value was accepted.
 * - ACCESS_DENIED: the write is not allowed in the current security state.
 */
public class SendBufferedData extends AbstractWriteAttribute
{

	public static final Characteristic<SendBufferedData> CHARACTERISTIC = MovisensCharacteristics.SEND_BUFFERED_DATA;
	
	private Boolean sendBufferedData;
	
	public Boolean getSendBufferedData()
	{
		return sendBufferedData;
	}
	
	public String getSendBufferedDataUnit()
	{
		return "";
	}
	
	public SendBufferedData(Boolean sendBufferedData)
	{
		this.sendBufferedData = sendBufferedData;
		GattByteBuffer bb = GattByteBuffer.allocate(1);
		bb.putBoolean(sendBufferedData);
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
		return getSendBufferedData().toString();
	}
}
