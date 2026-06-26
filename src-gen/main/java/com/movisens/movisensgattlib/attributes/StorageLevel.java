package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

/**
 * The current level of storage used. 100% means that all free space is used (storage full) while 0% means that the whole storage capacity is available.
 */
public class StorageLevel extends AbstractReadAttribute
{

	public static final Characteristic<StorageLevel> CHARACTERISTIC = MovisensCharacteristics.STORAGE_LEVEL;
	
	private Double level;
	
	public Double getLevel()
	{
		return level;
	}
	
	public String getLevelUnit()
	{
		return "%";
	}
	
	public StorageLevel(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		level = (double) bb.getUint8();
	}

	@Override
	public Characteristic<StorageLevel> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		return getLevel().toString() + getLevelUnit();
	}
}
