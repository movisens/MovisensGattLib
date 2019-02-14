package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class RespiratoryMovement extends AbstractReadAttribute
{

	public static final Characteristic<RespiratoryMovement> CHARACTERISTIC = MovisensCharacteristics.RESPIRATORY_MOVEMENT;
	
	private Long sampleTimestamp;
	private Long sendTimestamp;
	
	public Long getSampleTimestamp()
	{
		return sampleTimestamp;
	}
	
	public String getSampleTimestampUnit()
	{
		return "";
	}
	
	public Long getSendTimestamp()
	{
		return sendTimestamp;
	}
	
	public String getSendTimestampUnit()
	{
		return "";
	}
	
	public RespiratoryMovement(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		sampleTimestamp = bb.getInt64();
		sendTimestamp = bb.getInt64();
	}

	@Override
	public Characteristic<RespiratoryMovement> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		return "sampleTimestamp = " + getSampleTimestamp() + ", " + "sendTimestamp = " + getSendTimestamp();
	}
}
