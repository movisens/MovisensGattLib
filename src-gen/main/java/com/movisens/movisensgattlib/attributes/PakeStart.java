package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractWriteAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;
import com.movisens.smartgattlib.helper.PlainTextAttribute;

public class PakeStart extends AbstractWriteAttribute implements PlainTextAttribute
{

	public static final Characteristic<PakeStart> CHARACTERISTIC = MovisensCharacteristics.PAKE_START;
	
	private Boolean start;
	
	public Boolean getStart()
	{
		return start;
	}
	
	public String getStartUnit()
	{
		return "";
	}
	
	public PakeStart(Boolean start)
	{
		this.start = start;
		GattByteBuffer bb = GattByteBuffer.allocate(1);
		bb.putBoolean(start);
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
		return getStart().toString();
	}
}
