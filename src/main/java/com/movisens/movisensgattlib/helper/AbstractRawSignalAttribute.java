package com.movisens.movisensgattlib.helper;

import com.movisens.smartgattlib.helper.AbstractAttribute;

public abstract class AbstractRawSignalAttribute extends AbstractAttribute {

    abstract public Integer getPacketCounter();

    abstract public Double getSampleRate();

    abstract public String[] getValueNames();

    abstract public String[] getValueUnits();

    abstract public double[] getValues();
}
