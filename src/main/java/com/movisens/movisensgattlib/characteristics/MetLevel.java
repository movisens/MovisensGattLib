package com.movisens.movisensgattlib.characteristics;

import com.movisens.movisensgattlib.characteristics.base.AbstractReadOnlyCharacteristic;
import com.movisens.smartgattlib.GattByteBuffer;

public class MetLevel extends AbstractReadOnlyCharacteristic<Integer[]> {
    private int sedentary = -1;
    private int light = -1;
    private int moderate = -1;
    private int vigorous = -1;

    public MetLevel(byte[] value) {
        super(value);
    }

    public boolean isValid() {
        return sedentary >= 0 && light >= 0 && moderate >= 0 && vigorous >= 0;
    }

    @Override
    protected Integer[] getValueForBytes(byte[] bytes) {
        GattByteBuffer gattByteBuffer = GattByteBuffer.wrap(bytes);
        this.sedentary = gattByteBuffer.getUint8().intValue();
        this.light = gattByteBuffer.getUint8().intValue();
        this.moderate = gattByteBuffer.getUint8().intValue();
        this.vigorous = gattByteBuffer.getUint8().intValue();
        return new Integer[]{sedentary, light, moderate, vigorous};
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

}
