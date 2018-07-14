package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class Rmssd extends AbstractReadAttribute
{

	public static final Characteristic<Rmssd> CHARACTERISTIC = MovisensCharacteristics.RMSSD;
	
	private Double rmssd;
	
	public Double getRmssd()
	{
		return rmssd;
	}
	
	public String getRmssdUnit()
	{
		return "ms";
	}
	
	public Rmssd(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		rmssd = new Double(bb.getInt16());
	}

	@Override
	public Characteristic<Rmssd> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		return getRmssd().toString() + getRmssdUnit();
	}
}
