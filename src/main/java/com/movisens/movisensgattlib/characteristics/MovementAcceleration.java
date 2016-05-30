package com.movisens.movisensgattlib.characteristics;

import com.movisens.smartgattlib.GattByteBuffer;

public class MovementAcceleration {
	Double value = Double.NaN;

	Double lsbValue = 1.0 / 256; // [g]

	public MovementAcceleration(byte[] value) {
		this.value = GattByteBuffer.wrap(value).getUint16().doubleValue()
				* lsbValue;
	}

	public Double getValue() {
		return value;
	}
}
