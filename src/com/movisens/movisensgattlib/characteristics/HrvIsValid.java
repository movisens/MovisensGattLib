package com.movisens.movisensgattlib.characteristics;

import com.movisens.smartgattlib.GattByteBuffer;

public class HrvIsValid {
	boolean valid = false;

	public HrvIsValid(byte[] value) {
		this.valid = GattByteBuffer.wrap(value).getUint8() > 0;
	}

	public boolean getValue() {
		return valid;
	}
}
