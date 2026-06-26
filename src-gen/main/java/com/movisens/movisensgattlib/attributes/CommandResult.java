package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;
import com.movisens.smartgattlib.helper.PlainTextAttribute;

/**
 * Result of the last command.
 * See the writable characteristic documentation for the possible results of each write.
 */
public class CommandResult extends AbstractReadAttribute implements PlainTextAttribute
{

	public static final Characteristic<CommandResult> CHARACTERISTIC = MovisensCharacteristics.COMMAND_RESULT;
	
	private EnumCommandResult error;
	
	public EnumCommandResult getError()
	{
		return error;
	}
	
	public String getErrorUnit()
	{
		return "";
	}
	
	public CommandResult(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		error = EnumCommandResult.getByValue(bb.getUint8());
	}

	@Override
	public Characteristic<CommandResult> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		return getError().toString();
	}
}
