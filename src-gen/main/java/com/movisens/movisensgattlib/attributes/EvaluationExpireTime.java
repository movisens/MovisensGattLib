package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

/**
 * End of evaluation period or 0 if no evaluation limit is set. Time is given in milliseconds since 1 January 1970 00:00:00 UTC.
 */
public class EvaluationExpireTime extends AbstractReadAttribute
{

	public static final Characteristic<EvaluationExpireTime> CHARACTERISTIC = MovisensCharacteristics.EVALUATION_EXPIRE_TIME;
	
	private java.util.Date time;
	
	public java.util.Date getTime()
	{
		return time;
	}
	
	public String getTimeUnit()
	{
		return "";
	}
	
	public EvaluationExpireTime(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		time = bb.getMstime();
	}

	@Override
	public Characteristic<EvaluationExpireTime> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		return getTime().toString();
	}
}
