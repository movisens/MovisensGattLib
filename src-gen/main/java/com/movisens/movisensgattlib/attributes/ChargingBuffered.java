package com.movisens.movisensgattlib.attributes;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class ChargingBuffered extends AbstractReadAttribute
{

	public static final Characteristic CHARACTERISTIC = MovisensCharacteristics.CHARGING_BUFFERED;
	
	public static final int periodLength = 10;
	private long time;
	private Boolean charging[];
	
	public Boolean[] getCharging()
	{
		return charging;
	}
	
	public String getChargingUnit()
	{
		return "";
	}
	
	public ChargingBuffered(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		
		time = bb.getUint32();
		short numValues = bb.getUint8();
		
		charging = new Boolean[numValues];
		
		for (int i = 0; i < numValues; i++)
		{
			charging[i] = bb.getBoolean();
		}
	}

	@Override
	public Characteristic getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		String result = "";
		for(int i=0; i<charging.length; i++)
		{
			result += "Charging Buffered: " + "time=" + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date((time + (periodLength * i)) * 1000)) + "charging = " + getCharging()[i] + " " + getChargingUnit() + "\r\n";
		}
		return result;
	}
}
