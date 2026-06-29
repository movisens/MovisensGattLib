package com.movisens.movisensgattlib.attributes;

public enum EnumCommandResult
{
	OK                            ((short)0, "OK"),
	NOT_STARTED_BATTERY_LOW       ((short)1, "NOT_STARTED_BATTERY_LOW"),
	NOT_STARTED_DATA_AVAILABLE    ((short)2, "NOT_STARTED_DATA_AVAILABLE"),
	NOT_STARTED_PROBAND_INFO_MISSING((short)3, "NOT_STARTED_PROBAND_INFO_MISSING"),
	NOT_DELETED_MEASUREMENT_ON    ((short)4, "NOT_DELETED_MEASUREMENT_ON"),
	NOT_STARTED_MEASUREMENT_ON    ((short)5, "NOT_STARTED_MEASUREMENT_ON"),
	NOT_STOPPED_MEASUREMENT_OFF   ((short)6, "NOT_STOPPED_MEASUREMENT_OFF"),
	NOT_ACTIVATED_PARTICIPANT_INFO_MISSING((short)7, "NOT_ACTIVATED_PARTICIPANT_INFO_MISSING"),
	NOT_STARTED_DEMO_TRIAL_PERIOD_EXPIRED((short)8, "NOT_STARTED_DEMO_TRIAL_PERIOD_EXPIRED"),
	INVALID_CALL_PARAMETERS       ((short)9, "INVALID_CALL_PARAMETERS"),
	UNEXPECTED_EXCEPTION          ((short)10, "UNEXPECTED_EXCEPTION"),
	SENSOR_RUNLEVEL_TOO_LOW       ((short)11, "SENSOR_RUNLEVEL_TOO_LOW"),
	ACCESS_DENIED                 ((short)13, "ACCESS_DENIED"),
	EVALUATION_PERIOD_EXPIRED     ((short)14, "EVALUATION_PERIOD_EXPIRED"),
	NOT_IMPLEMENTED               ((short)16, "NOT_IMPLEMENTED"),
	UNSUPPORTED_SECURITY_VERSION  ((short)17, "UNSUPPORTED_SECURITY_VERSION"),
	INVALID_PAKE_STATE            ((short)18, "INVALID_PAKE_STATE"),
	INVALID_POINT                 ((short)19, "INVALID_POINT"),
	INVALID_TRANSCRIPT            ((short)20, "INVALID_TRANSCRIPT"),
	KEY_CONFIRMATION_FAILED       ((short)21, "KEY_CONFIRMATION_FAILED"),
	WRONG_CODE                    ((short)22, "WRONG_CODE"),
	RETRY_REQUIRED_NEW_SESSION    ((short)23, "RETRY_REQUIRED_NEW_SESSION"),
	PAKE_RATE_LIMITED_60_MIN      ((short)24, "PAKE_RATE_LIMITED_60_MIN"),
	PAKE_RATE_LIMITED_24_H        ((short)28, "PAKE_RATE_LIMITED_24_H"),
	INVALID                       ((short)29, "invalid");

	private final short value;
	private final String name;

	EnumCommandResult(short value, String name)
	{
		this.value = value;
		this.name = name;
	}

	public short getValue()
	{
		return value;
	}
	
	public String getName()
	{
		return name;
	}

	public static EnumCommandResult getByValue(short value)
	{
		switch (value)
		{
			case 0:
				return EnumCommandResult.OK;
			case 1:
				return EnumCommandResult.NOT_STARTED_BATTERY_LOW;
			case 2:
				return EnumCommandResult.NOT_STARTED_DATA_AVAILABLE;
			case 3:
				return EnumCommandResult.NOT_STARTED_PROBAND_INFO_MISSING;
			case 4:
				return EnumCommandResult.NOT_DELETED_MEASUREMENT_ON;
			case 5:
				return EnumCommandResult.NOT_STARTED_MEASUREMENT_ON;
			case 6:
				return EnumCommandResult.NOT_STOPPED_MEASUREMENT_OFF;
			case 7:
				return EnumCommandResult.NOT_ACTIVATED_PARTICIPANT_INFO_MISSING;
			case 8:
				return EnumCommandResult.NOT_STARTED_DEMO_TRIAL_PERIOD_EXPIRED;
			case 9:
				return EnumCommandResult.INVALID_CALL_PARAMETERS;
			case 10:
				return EnumCommandResult.UNEXPECTED_EXCEPTION;
			case 11:
				return EnumCommandResult.SENSOR_RUNLEVEL_TOO_LOW;
			case 13:
				return EnumCommandResult.ACCESS_DENIED;
			case 14:
				return EnumCommandResult.EVALUATION_PERIOD_EXPIRED;
			case 16:
				return EnumCommandResult.NOT_IMPLEMENTED;
			case 17:
				return EnumCommandResult.UNSUPPORTED_SECURITY_VERSION;
			case 18:
				return EnumCommandResult.INVALID_PAKE_STATE;
			case 19:
				return EnumCommandResult.INVALID_POINT;
			case 20:
				return EnumCommandResult.INVALID_TRANSCRIPT;
			case 21:
				return EnumCommandResult.KEY_CONFIRMATION_FAILED;
			case 22:
				return EnumCommandResult.WRONG_CODE;
			case 23:
				return EnumCommandResult.RETRY_REQUIRED_NEW_SESSION;
			case 24:
				return EnumCommandResult.PAKE_RATE_LIMITED_60_MIN;
			case 28:
				return EnumCommandResult.PAKE_RATE_LIMITED_24_H;
			default:
				return EnumCommandResult.INVALID;
		}
	}
	
	@Override
	public String toString()
	{
		return name;
	}
}
