package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractWriteAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

/**
 * Writing this characteristic deletes data of the last measurement so the next measurement can be started.
 * This triggers a command_result notification.
 * Possible results:
 * - OK: the data delete command was accepted.
 * - NOT_DELETED_MEASUREMENT_ON: measurement is active, delayed or paused.
 * - ACCESS_DENIED: the write is not allowed in the current security state.
 */
public class DeleteData extends AbstractWriteAttribute
{

	public static final Characteristic<DeleteData> CHARACTERISTIC = MovisensCharacteristics.DELETE_DATA;
	
	
	public DeleteData()
	{
		GattByteBuffer bb = GattByteBuffer.allocate(1);
		bb.putInt8((byte) 0);
		this.data = bb.array();
	}

	@Override
	public Characteristic<DeleteData> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		return "DELETE_DATA";
	}
}
