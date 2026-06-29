package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

/**
 * Returns true if at least one measurement is stored on the sensor and
 * the measurement data can be downloaded from the sensor via USB.
 * This attribute does not relate to buffered attribute data used in BLE.
 */
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
