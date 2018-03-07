package com.movisens.movisensgattlib.helper;

public class AbstractData
{

    long localTime;

    long sampleTime;

    int periodlength;

    BufferedCharacteristic<?, ?> characteristic;

    public AbstractData(long localTime, long sampleTime, int periodlength, BufferedCharacteristic<?, ?> characteristic)
    {
        super();
        this.localTime = localTime;
        this.sampleTime = sampleTime;
        this.periodlength = periodlength;
        this.characteristic = characteristic;
    }

    public BufferedCharacteristic<?, ?> getCharacteristic()
    {
        return characteristic;
    }

}
