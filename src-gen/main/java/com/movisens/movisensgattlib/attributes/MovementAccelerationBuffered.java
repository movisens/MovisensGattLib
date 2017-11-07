package com.movisens.movisensgattlib.attributes;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class MovementAccelerationBuffered extends AbstractReadAttribute
{

	public static final Characteristic CHARACTERISTIC = MovisensCharacteristics.MOVEMENT_ACCELERATION_BUFFERED;
	
	public static final int periodLength = 60;
	private long time;
	private Double movementAcceleration[];
	
	public Double[] getMovementAcceleration()
	{
		return movementAcceleration;
	}
	
	public String getMovementAccelerationUnit()
	{
		return "g";
	}
	
	public MovementAccelerationBuffered(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		
		time = bb.getUint32();
		short numValues = bb.getUint8();
		
		movementAcceleration = new Double[numValues];
		
		for (int i = 0; i < numValues; i++)
		{
			movementAcceleration[i] = ((double)bb.getInt16()) * 0.00390625;
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
		for(int i=0; i<movementAcceleration.length; i++)
		{
			result += "Movement Acceleration Buffered: " + "time=" + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date((time + (periodLength * i)) * 1000)) + "movementAcceleration = " + getMovementAcceleration()[i] + " " + getMovementAccelerationUnit() + "\r\n";
		}
		return result;
	}
}
