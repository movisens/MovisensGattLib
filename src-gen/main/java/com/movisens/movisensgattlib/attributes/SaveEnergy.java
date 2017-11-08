package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractWriteAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class SaveEnergy extends AbstractWriteAttribute
{

	public static final Characteristic CHARACTERISTIC = MovisensCharacteristics.SAVE_ENERGY;
	
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
	public Characteristic getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		return "Save Energy: " + "saveEnergy = " + getSaveEnergy();
	}
}
