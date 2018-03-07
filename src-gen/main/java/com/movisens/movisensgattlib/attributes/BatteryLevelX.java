package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class BatteryLevelX extends AbstractReadAttribute
{

	public static final Characteristic<BatteryLevelX> CHARACTERISTIC = MovisensCharacteristics.BATTERY_LEVEL_X;
	
	private Double batteryLevel;
	
	public Double getBatteryLevel()
	{
		return batteryLevel;
	}
	
	public String getBatteryLevelUnit()
	{
		return "%";
	}
	
	public BatteryLevelX(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		batteryLevel = ((double)bb.getUint32()) * 1.0E-6;
	}

	@Override
	public Characteristic<BatteryLevelX> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		return getBatteryLevel().toString() + getBatteryLevelUnit();
	}
}
