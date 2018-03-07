package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class Counter extends AbstractReadAttribute
{

	public static final Characteristic<Counter> CHARACTERISTIC = MovisensCharacteristics.COUNTER;
	
	private Long counter;
	
	public Long getCounter()
	{
		return counter;
	}
	
	public String getCounterUnit()
	{
		return "";
	}
	
	public Counter(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		counter = bb.getUint32();
	}

	@Override
	public Characteristic<Counter> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		return getCounter().toString();
	}
}
