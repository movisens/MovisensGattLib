package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadWriteAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class AgeFloat extends AbstractReadWriteAttribute
{

	public static final Characteristic CHARACTERISTIC = MovisensCharacteristics.AGE_FLOAT;
	
	private Float age;
	
	public Float getAge()
	{
		return age;
	}
	
	public String getAgeUnit()
	{
		return "";
	}
	
	public AgeFloat(Float age)
	{
		this.age = age;
		GattByteBuffer bb = GattByteBuffer.allocate(4);
		bb.putFloat32(age);
		this.data = bb.array();
	}

	public AgeFloat(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		age = bb.getFloat32();
	}

	@Override
	public Characteristic getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		return "Age Float: " + "age = " + getAge();
	}
}
