package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class BatteryVoltage extends AbstractReadAttribute
{

	public static final Characteristic<BatteryVoltage> CHARACTERISTIC = MovisensCharacteristics.BATTERY_VOLTAGE;
	
	private Double batteryLevel;
	
	public Double getBatteryLevel()
	{
		return batteryLevel;
	}
	
	public String getBatteryLevelUnit()
	{
		return "mV";
	}
	
	public BatteryVoltage(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		batteryLevel = new Double(bb.getUint32());
	}

	@Override
	public Characteristic<BatteryVoltage> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		return getBatteryLevel().toString() + getBatteryLevelUnit();
	}
}
