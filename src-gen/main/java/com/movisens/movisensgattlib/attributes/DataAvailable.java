package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class DataAvailable extends AbstractReadAttribute
{

	public static final Characteristic<DataAvailable> CHARACTERISTIC = MovisensCharacteristics.DATA_AVAILABLE;
	
	private Boolean dataAvailable;
	
	public Boolean getDataAvailable()
	{
		return dataAvailable;
	}
	
	public String getDataAvailableUnit()
	{
		return "";
	}
	
	public DataAvailable(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		dataAvailable = bb.getBoolean();
	}

	@Override
	public Characteristic<DataAvailable> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		return getDataAvailable().toString();
	}
}
