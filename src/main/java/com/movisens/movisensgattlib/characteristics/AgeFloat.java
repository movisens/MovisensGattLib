package com.movisens.movisensgattlib.characteristics;

import com.movisens.smartgattlib.GattByteBuffer;

public class AgeFloat {
	private byte[] bytes;
	private float value;

	/**
	 * 
	 * @param age
	 *            in years (for example 35,5)
	 */
	public AgeFloat(float age) {
		this.value = age;
		this.bytes = GattByteBuffer.allocate(4).putFloat32(age).array();
	}

	public AgeFloat(byte[] bytes) {
		this.bytes = bytes;
		this.value = GattByteBuffer.wrap(bytes).getFloat32();
	}

	public byte[] getBytes() {
		return bytes;
	}

	public float getValue() {
		return value;
	}

}
