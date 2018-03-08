package com.movisens.movisensgattlib.attributes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.movisensgattlib.helper.AbstractBufferedAttribute;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class MovementAccelerationBuffered extends AbstractBufferedAttribute<MovementAccelerationData>
{

	public static final BufferedCharacteristic<MovementAccelerationBuffered, MovementAccelerationData> CHARACTERISTIC = MovisensCharacteristics.MOVEMENT_ACCELERATION_BUFFERED;
	
	public static final int periodLength = 60;
	private long time;
	private Double movementAcceleration[];
	
	@Override
	public Date getTime()
	{
		return new Date(time*1000);
	}

	@Override
	public double getSamplerate()
	{
		return 1.0/periodLength;
	}

	@Override
	public String[] getValueNames()
	{
		String[] names = {"movementAcceleration"};
		return names;
	}

	@Override
	public String[] getValueUnits()
	{
		String[] names = {"g"};
		return names;
	}
	
	@Override
	public double[][] getValues()
	{
		int numSamples = movementAcceleration.length;
		double[][] data = new double[numSamples][1];
		
		for(int i=0; i<numSamples; i++)
		{
			data[i][0] = movementAcceleration[i];
		}
		
		return data;
	}

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
	public BufferedCharacteristic<MovementAccelerationBuffered, MovementAccelerationData> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		String result = "";
		for(int i=0; i<movementAcceleration.length; i++)
		{
			result += "time = " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date((time + (periodLength * i)) * 1000)) + ", " + getMovementAcceleration()[i].toString() + getMovementAccelerationUnit() + " \r\n";
		}
		return result;
	}

	@Override
	public Iterable<MovementAccelerationData> getData()
	{
	    Vector<MovementAccelerationData> datas = new Vector<MovementAccelerationData>();
	    long now = new Date().getTime();
	    
	    for(int i=0; i<movementAcceleration.length; i++)
	    {
	        datas.add(new MovementAccelerationData(now, (time + (periodLength * i)) * 1000, periodLength, getMovementAcceleration()[i]));
	    }
	    
	    return datas;
	}
}
