package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.helper.AbstractData;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;
import com.movisens.movisensgattlib.MovisensCharacteristics;

public class ActivityclassData extends AbstractData
{
	private Short activityClass;
	
	public Short getActivityClass()
	{
		return activityClass;
	}
	
    public ActivityclassData(long localTime, long sampleTime, int periodlength, Short activityClass)
    {
        super(localTime, sampleTime, periodlength);
		this.activityClass = activityClass;
    }
	
    @Override
    public BufferedCharacteristic<?, ?> getCharacteristic()
    {
        return MovisensCharacteristics.ACTIVITYCLASS_BUFFERED;
    }
}
