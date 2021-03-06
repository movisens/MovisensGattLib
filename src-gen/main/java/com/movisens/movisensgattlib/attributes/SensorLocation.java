package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadWriteAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class SensorLocation extends AbstractReadWriteAttribute
{

	public static final Characteristic<SensorLocation> CHARACTERISTIC = MovisensCharacteristics.SENSOR_LOCATION;
	
	private EnumSensorLocation sensorLocation;
	
	public EnumSensorLocation getSensorLocation()
	{
		return sensorLocation;
	}
	
	public String getSensorLocationUnit()
	{
		return "";
	}
	
	public SensorLocation(EnumSensorLocation sensorLocation)
	{
		this.sensorLocation = sensorLocation;
		GattByteBuffer bb = GattByteBuffer.allocate(1);
		bb.putUint8(sensorLocation.getValue());
		this.data = bb.array();
	}

	public SensorLocation(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		sensorLocation = EnumSensorLocation.getByValue(bb.getUint8());
	}

	@Override
	public Characteristic<SensorLocation> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		return getSensorLocation().toString();
	}
}
