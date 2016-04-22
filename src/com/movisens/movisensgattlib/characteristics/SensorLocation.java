package com.movisens.movisensgattlib.characteristics;

import com.movisens.smartgattlib.GattByteBuffer;

public class SensorLocation {
	public static enum Position {
		RIGHT_SIDE_HIP(1), CHEST(2), RIGHT_WRIST(3), LEFT_WRIST(4), LEFT_ANKLE(5), RIGHT_ANKLE(6), RIGHT_THIGH(
				7), LEFT_THIGH(8), RIGHT_UPPER_ARM(9), LEFT_UPPER_ARM(10), LEFT_SIDE_HIP(11);

		public final int value;

		private Position(int value) {
			this.value = value;
		}
	}

	private byte[] value;

	public SensorLocation(Position position) {
		this.value = GattByteBuffer.allocate(4).putUint16(position.value).array();
	}

	public byte[] getValue() {
		return value;
	}

}
