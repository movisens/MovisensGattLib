package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class EdaSclMean extends AbstractReadAttribute
{

	public static final Characteristic<EdaSclMean> CHARACTERISTIC = MovisensCharacteristics.EDA_SCL_MEAN;
	
	private Integer edaSclMean;
	
	public Integer getEdaSclMean()
	{
		return edaSclMean;
	}
	
	public String getEdaSclMeanUnit()
	{
		return "";
	}
	
	public EdaSclMean(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		edaSclMean = bb.getUint16();
	}

	@Override
	public Characteristic<EdaSclMean> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		return getEdaSclMean().toString();
	}
}
