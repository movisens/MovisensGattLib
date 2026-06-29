package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

/**
 * The current battery voltage.
 */
public class BatteryVoltage extends AbstractReadAttribute
{

	public static final Characteristic<BatteryVoltage> CHARACTERISTIC = MovisensCharacteristics.BATTERY_VOLTAGE;
	
	private Double voltage;
	
	public Double getVoltage()
	{
		return voltage;
	}
	
	public String getVoltageUnit()
	{
		return "mV";
	}
	
	public BatteryVoltage(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		voltage = (double) bb.getUint16();
	}

	@Override
	public Characteristic<BatteryVoltage> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		return getVoltage().toString() + getVoltageUnit();
	}
}
