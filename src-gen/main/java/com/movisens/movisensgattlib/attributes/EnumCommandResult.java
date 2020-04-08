package com.movisens.movisensgattlib.attributes;

public enum EnumCommandResult
{
	OK                            ((short)0, "ok"),
	NOT_STARTED_BATTERY_LOW       ((short)1, "not_started_battery_low"),
	NOT_STARTED_DATA_AVAILABLE    ((short)2, "not_started_data_available"),
	NOT_STARTED_PROBAND_INFO_MISSING((short)3, "not_started_proband_info_missing"),
	NOT_DELETED_MEASUREMENT_ACTIVE((short)4, "not_deleted_measurement_active"),
	INVALID                       ((short)5, "invalid");

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
				return EnumCommandResult.NOT_DELETED_MEASUREMENT_ACTIVE;
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
