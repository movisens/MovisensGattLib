package com.movisens.movisensgattlib.characteristics;

import com.movisens.smartgattlib.GattByteBuffer;

public class MetLevel {
	Integer sedetary = -1;
	Integer light = -1;
	Integer moderate = -1;
	Integer vigorous = -1;

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

	public Integer getSedetaryValue() {
		return sedetary;
	}

	public Integer getLightValue() {
		return light;
	}

	public Integer getModerateValue() {
		return moderate;
	}

	public Integer getVigorousValue() {
		return vigorous;
	}

	public Integer[] getValue() {
		return new Integer[] { sedetary, light, moderate, vigorous };
	}

}
