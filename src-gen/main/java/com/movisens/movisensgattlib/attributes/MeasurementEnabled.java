package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadWriteAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class MeasurementEnabled extends AbstractReadWriteAttribute
{

	public static final Characteristic CHARACTERISTIC = MovisensCharacteristics.MEASUREMENT_ENABLED;
	
	private Boolean measurementEnabled;
	
	public Boolean getMeasurementEnabled()
	{
		return measurementEnabled;
	}
	
	public String getMeasurementEnabledUnit()
	{
		return "";
	}
	
	public MeasurementEnabled(Boolean measurementEnabled)
	{
		this.measurementEnabled = measurementEnabled;
		GattByteBuffer bb = GattByteBuffer.allocate(1);
		bb.putBoolean(measurementEnabled);
		this.data = bb.array();
	}

	public MeasurementEnabled(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		measurementEnabled = bb.getBoolean();
	}

	@Override
	public Characteristic getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		return "Measurement Enabled: " + "measurementEnabled = " + getMeasurementEnabled() + " " + getMeasurementEnabledUnit();
	}
}
