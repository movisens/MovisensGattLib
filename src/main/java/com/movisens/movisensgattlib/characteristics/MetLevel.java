package com.movisens.movisensgattlib.characteristics;

import com.movisens.smartgattlib.GattByteBuffer;

public class MetLevel {
    private int sedentary = -1;
    private int light = -1;
    private int moderate = -1;
    private int vigorous = -1;

    public MetLevel(byte[] value) {
        GattByteBuffer gattByteBuffer = GattByteBuffer.wrap(value);

        this.sedentary = gattByteBuffer.getUint8().intValue();
        this.light = gattByteBuffer.getUint8().intValue();
        this.moderate = gattByteBuffer.getUint8().intValue();
        this.vigorous = gattByteBuffer.getUint8().intValue();
    }

    public boolean isValid() {
        return sedentary >= 0 && light >= 0 && moderate >= 0 && vigorous >= 0;
    }

    public int getSedentaryValue() {
        return sedentary;
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
        return new int[]{sedentary, light, moderate, vigorous};
    }

}
