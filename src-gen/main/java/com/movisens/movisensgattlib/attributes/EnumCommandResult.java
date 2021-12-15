package com.movisens.movisensgattlib.attributes;

public enum EnumCommandResult
{
	OK                            ((short)0, "ok"),
	NOT_STARTED_BATTERY_LOW       ((short)1, "not_started_battery_low"),
	NOT_STARTED_DATA_AVAILABLE    ((short)2, "not_started_data_available"),
	NOT_STARTED_PROBAND_INFO_MISSING((short)3, "not_started_proband_info_missing"),
	NOT_DELETED_MEASUREMENT_ON    ((short)4, "not_deleted_measurement_on"),
	NOT_STARTED_MEASUREMENT_ON    ((short)5, "not_started_measurement_on"),
	NOT_STOPPED_MEASUREMENT_OFF   ((short)6, "not_stopped_measurement_off"),
	NOT_ACTIVATED_PARTICIPANT_INFO_MISSING((short)7, "not_activated_participant_info_missing"),
	NOT_STARTED_DEMO_TRIAL_PERIOD_EXPIRED((short)8, "NOT_STARTED_DEMO_TRIAL_PERIOD_EXPIRED"),
	INVALID_CALL_PARAMETERS       ((short)9, "INVALID_CALL_PARAMETERS"),
	UNEXPECTED_EXCEPTION          ((short)10, "UNEXPECTED_EXCEPTION"),
	SENSOR_RUNLEVEL_TOO_LOW       ((short)11, "SENSOR_RUNLEVEL_TOO_LOW"),
	LOGIN_KEY_INVALID             ((short)12, "LOGIN_KEY_INVALID"),
	ACCESS_DENIED                 ((short)13, "ACCESS_DENIED"),
	EVALUATION_PERIOD_EXPIRED     ((short)14, "EVALUATION_PERIOD_EXPIRED"),
	INVALID                       ((short)15, "invalid");

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
			case 12:
				return EnumCommandResult.LOGIN_KEY_INVALID;
			case 13:
				return EnumCommandResult.ACCESS_DENIED;
			case 14:
				return EnumCommandResult.EVALUATION_PERIOD_EXPIRED;
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
