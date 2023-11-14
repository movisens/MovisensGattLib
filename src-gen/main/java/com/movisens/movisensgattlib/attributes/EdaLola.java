package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class EdaLola extends AbstractReadAttribute
{

	public static final Characteristic<EdaLola> CHARACTERISTIC = MovisensCharacteristics.EDA_LOLA;
	
	private Short[] values;
	
	public Short[] getValues()
	{
		return values;
	}
	
	private String getValuesString()
	{
			String result = "[";
			for(short value : getValues()) {
			    result += value + " ";
			}
			result += "]";
			return result;
	}
	
	public String getValuesUnit()
	{
		return "";
	}
	
	public EdaLola(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		values = new Short[2];
		for (int i = 0; i < 2; i++)
		{
			values[i] = bb.getInt16();
		}
	}

	@Override
	public Characteristic<EdaLola> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		return getValuesString();
	}
}
