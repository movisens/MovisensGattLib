package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractWriteAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

/**
 * If set to 1, data of the last measurement is deleted and the next measurement can be started.
 * This triggers a command_result notification.
 * Possible results:
 * - OK: the data delete command was accepted.
 * - NOT_DELETED_MEASUREMENT_ON: measurement is active, delayed or paused.
 * - ACCESS_DENIED: the write is not allowed in the current security state.
 */
public class DeleteData extends AbstractWriteAttribute
{

	public static final Characteristic<DeleteData> CHARACTERISTIC = MovisensCharacteristics.DELETE_DATA;
	
	private Boolean deleteData;
	
	public Boolean getDeleteData()
	{
		return deleteData;
	}
	
	public String getDeleteDataUnit()
	{
		return "";
	}
	
	public DeleteData(Boolean deleteData)
	{
		this.deleteData = deleteData;
		GattByteBuffer bb = GattByteBuffer.allocate(1);
		bb.putBoolean(deleteData);
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
		return getDeleteData().toString();
	}
}
