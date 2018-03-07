package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractWriteAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

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
