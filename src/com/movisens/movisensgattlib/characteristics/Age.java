package com.movisens.movisensgattlib.characteristics;

import java.util.Calendar;

import com.movisens.smartgattlib.GattByteBuffer;

public class Age {
	private byte[] value;

	/**
	 * 
	 * @param age
	 *            in days
	 */
	public Age(float age) {
		this.value = GattByteBuffer.allocate(4).putFloat32(age).array();
	}

	public Age(Calendar birthDate) {
		long ageInMillis = System.currentTimeMillis() - birthDate.getTimeInMillis();
		float age = ageInMillis / 1000 / 60 / 60 / 24 / 365;
		this.value = GattByteBuffer.allocate(4).putFloat32(age).array();
	}

	public byte[] getValue() {
		return value;
	}

}
