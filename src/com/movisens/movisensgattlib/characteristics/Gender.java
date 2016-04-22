package com.movisens.movisensgattlib.characteristics;

import com.movisens.smartgattlib.GattByteBuffer;

public class Gender {
	public static enum Sex {
		MALE(1), FEMALE(2);

		public final int value;

		private Sex(int value) {
			this.value = value;
		}
	}

	private byte[] value;

	public Gender(Sex sex) {
		this.value = GattByteBuffer.allocate(4).putUint16(sex.value).array();
	}

	public byte[] getValue() {
		return value;
	}

}
