package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class Ecg extends AbstractReadAttribute
{

	public static final Characteristic CHARACTERISTIC = MovisensCharacteristics.ECG;
	
	private Short ecgFirst;
	private Byte ecgDiff;
	
	public Short getEcgFirst()
	{
		return ecgFirst;
	}
	
	public String getEcgFirstUnit()
	{
		return "";
	}
	
	public Byte getEcgDiff()
	{
		return ecgDiff;
	}
	
	public String getEcgDiffUnit()
	{
		return "";
	}
	
	public Ecg(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		ecgFirst = bb.getInt16();
		ecgDiff = bb.getInt8();
	}

	@Override
	public Characteristic getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		return "Ecg: " + "ecgFirst = " + getEcgFirst() + ", " + "ecgDiff = " + getEcgDiff();
	}
}
