package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.helper.AbstractData;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;

public class ActivityclassData extends AbstractData
{
	private Short activityClass;
	
	public Short getActivityClass()
	{
		return activityClass;
	}
	
    public ActivityclassData(long localTime, long sampleTime, int periodlength, BufferedCharacteristic<ActivityclassBuffered, ActivityclassData> characteristic, Short activityClass)
    {
        super(localTime, sampleTime, periodlength, characteristic);
		this.activityClass = activityClass;
    }
	
}
