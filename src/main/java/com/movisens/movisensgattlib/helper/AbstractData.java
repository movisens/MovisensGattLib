package com.movisens.movisensgattlib.helper;

public abstract class AbstractData
{

    private long arrivalTime;

    private long sampleTime;

    private int periodLength;

    public AbstractData(long arrivalTime, long sampleTime, int periodLength)
    {
        super();
        this.arrivalTime = arrivalTime;
        this.sampleTime = sampleTime;
        this.periodLength = periodLength;
    }

    public long getArrivalTime()
    {
        return arrivalTime;
    }

    @Deprecated
    public long getArivalTime()
    {
        return getArrivalTime();
    }

    public long getSampleTime()
    {
        return sampleTime;
    }

    public int getPeriodLength()
    {
        return periodLength;
    }

    @Deprecated
    public int getPeriodlength()
    {
        return getPeriodLength();
    }

    public abstract BufferedCharacteristic<?, ?> getCharacteristic();

}
