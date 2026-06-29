package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractWriteAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

/**
 * Seals the sensor and sets the given key.
 * This triggers a command_result notification.
 * Possible results:
 * - OK: the sensor was sealed.
 * - ACCESS_DENIED: the connection is not encrypted, not authenticated or not allowed
 *   to seal in the current security state.
 */
public class SealSensor extends AbstractWriteAttribute
{

	public static final Characteristic<SealSensor> CHARACTERISTIC = MovisensCharacteristics.SEAL_SENSOR;
	
	private Long key;
	
	public Long getKey()
	{
		return key;
	}
	
	public String getKeyUnit()
	{
		return "";
	}
	
	public SealSensor(Long key)
	{
		this.key = key;
		GattByteBuffer bb = GattByteBuffer.allocate(8);
		bb.putInt64(key);
		this.data = bb.array();
	}

	@Override
	public Characteristic<SealSensor> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		return getKey().toString();
	}
}
