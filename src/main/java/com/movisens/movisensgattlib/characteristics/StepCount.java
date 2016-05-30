package com.movisens.movisensgattlib.characteristics;

import com.movisens.smartgattlib.GattByteBuffer;

public class StepCount {
	Integer value = -1;

	public StepCount(byte[] value) {
		this.value = GattByteBuffer.wrap(value).getUint16();
	}

	public Integer getValue() {
		return value;
	}

	public boolean isValid() {
		return value >= 0;
	}
}
