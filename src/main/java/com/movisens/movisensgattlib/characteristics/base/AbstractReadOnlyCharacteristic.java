package com.movisens.movisensgattlib.characteristics.base;

/**
 * Created by Robert Zetzsche on 07.03.2017.
 */

public abstract class AbstractReadOnlyCharacteristic<T> {
    protected T value;

    AbstractReadOnlyCharacteristic() {
    }

    public AbstractReadOnlyCharacteristic(byte[] bytes) {
        this.value = getValueForBytes(bytes);
    }

    public T getValue() {
        return value;
    }

    public boolean isValid() {
        return true;
    }

    protected abstract T getValueForBytes(byte[] bytes);


}

