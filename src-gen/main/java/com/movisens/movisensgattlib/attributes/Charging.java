package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class Charging extends AbstractReadAttribute
{

	public static final Characteristic<Charging> CHARACTERISTIC = MovisensCharacteristics.CHARGING;
	
	private Boolean charging;
	
	public Boolean getCharging()
	{
		return charging;
	}
	
	public String getChargingUnit()
	{
		return "";
	}
	
	public Charging(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		charging = bb.getBoolean();
	}

	@Override
	public Characteristic<Charging> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		return getCharging().toString();
	}
}
