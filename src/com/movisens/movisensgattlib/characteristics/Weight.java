package com.movisens.movisensgattlib.characteristics;

import com.movisens.smartgattlib.GattByteBuffer;

public class Weight {
	private byte[] value;

	/**
	 * Constructor for new WeightCharacteristic
	 * 
	 * @param weight
	 *            in kg
	 */
	public Weight(float weight) {
		this.value = GattByteBuffer.allocate(4).putFloat32(weight).array();
	}

	public byte[] getValue() {
		return value;
	}

}
