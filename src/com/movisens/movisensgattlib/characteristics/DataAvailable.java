package com.movisens.movisensgattlib.characteristics;

import com.movisens.smartgattlib.GattByteBuffer;

public class DataAvailable {
	Boolean value = false;

	public DataAvailable(byte[] value) {
		this.value = GattByteBuffer.wrap(value).getUint8() > 0;
	}

	public Boolean getValue() {
		return value;
	}
}
