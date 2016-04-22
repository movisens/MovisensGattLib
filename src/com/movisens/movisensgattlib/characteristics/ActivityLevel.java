package com.movisens.movisensgattlib.characteristics;

import com.movisens.smartgattlib.GattByteBuffer;

public class ActivityLevel {
	Integer value = -1;

	public ActivityLevel(byte[] value) {
		this.value = GattByteBuffer.wrap(value).getUint8().intValue();
	}

	public Integer getValue() {
		return value;
	}

	public boolean isValid() {
		return value >= 0;
	}

}
