package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractWriteAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class DataRx extends AbstractWriteAttribute
{

	public static final Characteristic CHARACTERISTIC = MovisensCharacteristics.DATA_RX;
	
	private Short dataRx;
	
	public Short getDataRx()
	{
		return dataRx;
	}
	
	public String getDataRxUnit()
	{
		return "";
	}
	
	public DataRx(Short dataRx)
	{
		this.dataRx = dataRx;
		GattByteBuffer bb = GattByteBuffer.allocate(1);
		bb.putUint8(dataRx);
		this.data = bb.array();
	}

	@Override
	public Characteristic getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		return getDataRx().toString();
	}
}
