package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractWriteAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class DeleteData extends AbstractWriteAttribute
{

	public static final Characteristic CHARACTERISTIC = MovisensCharacteristics.DELETE_DATA;
	
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
	public Characteristic getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		return getDeleteData().toString();
	}
}
