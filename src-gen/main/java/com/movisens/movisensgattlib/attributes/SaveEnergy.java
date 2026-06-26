package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractWriteAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

/**
 * Must be set to 1 to put the connection into energy saving mode (Recommended after configuration is done). As soon as a new connection is established the save energy mode must be enabled again.
 * This triggers a command_result notification.
 * Possible results:
 * - ok: the value was accepted.
 * - ACCESS_DENIED: the write is not allowed in the current security state.
 */
public class SaveEnergy extends AbstractWriteAttribute
{

	public static final Characteristic<SaveEnergy> CHARACTERISTIC = MovisensCharacteristics.SAVE_ENERGY;
	
	private Boolean saveEnergy;
	
	public Boolean getSaveEnergy()
	{
		return saveEnergy;
	}
	
	public String getSaveEnergyUnit()
	{
		return "";
	}
	
	public SaveEnergy(Boolean saveEnergy)
	{
		this.saveEnergy = saveEnergy;
		GattByteBuffer bb = GattByteBuffer.allocate(1);
		bb.putBoolean(saveEnergy);
		this.data = bb.array();
	}

	@Override
	public Characteristic<SaveEnergy> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		return getSaveEnergy().toString();
	}
}
