package com.movisens.movisensgattlib.characteristics;

import com.movisens.smartgattlib.GattByteBuffer;

public class AgeFloat {
	private byte[] value;

	/**
	 * 
	 * @param age
	 *            in years (for example 35,5)
	 */
	public AgeFloat(float age) {
		this.value = GattByteBuffer.allocate(4).putFloat32(age).array();
	}

	public byte[] getValue() {
		return value;
	}

}
