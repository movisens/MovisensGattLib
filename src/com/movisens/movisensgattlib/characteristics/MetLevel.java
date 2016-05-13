package com.movisens.movisensgattlib.characteristics;

import com.movisens.smartgattlib.GattByteBuffer;

public class MetLevel {
	int sedetary = -1;
	int light = -1;
	int moderate = -1;
	int vigorous = -1;

	public MetLevel(byte[] value) {
		GattByteBuffer gattByteBuffer = GattByteBuffer.wrap(value);

		this.sedetary = gattByteBuffer.getUint8().intValue();
		this.light = gattByteBuffer.getUint8().intValue();
		this.moderate = gattByteBuffer.getUint8().intValue();
		this.vigorous = gattByteBuffer.getUint8().intValue();
	}

	public boolean isValid() {
		return sedetary >= 0 && light >= 0 && moderate >= 0 && vigorous >= 0;
	}

	public int getSedetaryValue() {
		return sedetary;
	}

	public int getLightValue() {
		return light;
	}

	public int getModerateValue() {
		return moderate;
	}

	public int getVigorousValue() {
		return vigorous;
	}

	public int[] getValue() {
		return new int[] { sedetary, light, moderate, vigorous };
	}

}
