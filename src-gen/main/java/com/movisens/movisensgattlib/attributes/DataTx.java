package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class DataTx extends AbstractReadAttribute
{

	public static final Characteristic CHARACTERISTIC = MovisensCharacteristics.DATA_TX;
	
	private Short dataTx;
	
	public Short getDataTx()
	{
		return dataTx;
	}
	
	public String getDataTxUnit()
	{
		return "";
	}
	
	public DataTx(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		dataTx = bb.getUint8();
	}

	@Override
	public Characteristic getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		return "Data Tx: " + "dataTx = " + getDataTx();
	}
}
