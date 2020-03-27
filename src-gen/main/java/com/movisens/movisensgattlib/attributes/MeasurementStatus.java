package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class MeasurementStatus extends AbstractReadAttribute
{

	public static final Characteristic<MeasurementStatus> CHARACTERISTIC = MovisensCharacteristics.MEASUREMENT_STATUS;
	
	private EnumMeasurementStatus status;
	
	public EnumMeasurementStatus getStatus()
	{
		return status;
	}
	
	public String getStatusUnit()
	{
		return "";
	}
	
	public MeasurementStatus(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		status = EnumMeasurementStatus.getByValue(bb.getUint8());
	}

	@Override
	public Characteristic<MeasurementStatus> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		return getStatus().toString();
	}
}
