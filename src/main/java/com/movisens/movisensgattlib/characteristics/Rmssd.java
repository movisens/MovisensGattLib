package com.movisens.movisensgattlib.characteristics;

import com.movisens.smartgattlib.GattByteBuffer;

public class Rmssd {
	Double value = Double.NaN;

	public Rmssd(byte[] value) {
		this.value = GattByteBuffer.wrap(value).getInt16().doubleValue();
		if (this.value == -1)
			this.value = Double.NaN;
	}

	public Double getValue() {
		return value;
	}

	public boolean isValid() {
		return !value.isNaN();
	}
}
