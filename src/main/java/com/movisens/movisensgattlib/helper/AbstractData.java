package com.movisens.movisensgattlib.helper;

public abstract class AbstractData
{

    private long arivalTime;

    private long sampleTime;

    private int periodlength;

    public AbstractData(long arivalTime, long sampleTime, int periodlength)
    {
        super();
        this.arivalTime = arivalTime;
        this.sampleTime = sampleTime;
        this.periodlength = periodlength;
    }

    
    public long getArivalTime()
    {
        return arivalTime;
    }

    
    public long getSampleTime()
    {
        return sampleTime;
    }

    
    public int getPeriodlength()
    {
        return periodlength;
    }

    public abstract BufferedCharacteristic<?, ?> getCharacteristic();

}
