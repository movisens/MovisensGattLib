package com.movisens.movisensgattlib.attributes;

public enum EnumMeasurementStatus
{
	STOPPED_DURATION_REACHED      ((short)1, "stopped_duration_reached"),
	STOPPED_USER_USB              ((short)2, "stopped_user_usb"),
	STOPPED_BATTERY_EMPTY         ((short)3, "stopped_battery_empty"),
	STOPPED_ERROR                 ((short)4, "stopped_error"),
	STOPPED_USER_BLE              ((short)5, "stopped_user_ble"),
	STOPPED_STORAGE_FULL          ((short)6, "stopped_storage_full"),
	PAUSED_BATTERY_LOW            ((short)7, "paused_battery_low"),
	STOPPED_EMPTY                 ((short)8, "stopped_empty"),
	MEASURING                     ((short)9, "measuring"),
	INVALID                       ((short)10, "invalid");

	private final short value;
	private final String name;

	EnumMeasurementStatus(short value, String name)
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

	public static EnumMeasurementStatus getByValue(short value)
	{
		switch (value)
		{
			case 1:
				return EnumMeasurementStatus.STOPPED_DURATION_REACHED;
			case 2:
				return EnumMeasurementStatus.STOPPED_USER_USB;
			case 3:
				return EnumMeasurementStatus.STOPPED_BATTERY_EMPTY;
			case 4:
				return EnumMeasurementStatus.STOPPED_ERROR;
			case 5:
				return EnumMeasurementStatus.STOPPED_USER_BLE;
			case 6:
				return EnumMeasurementStatus.STOPPED_STORAGE_FULL;
			case 7:
				return EnumMeasurementStatus.PAUSED_BATTERY_LOW;
			case 8:
				return EnumMeasurementStatus.STOPPED_EMPTY;
			case 9:
				return EnumMeasurementStatus.MEASURING;
			default:
				return EnumMeasurementStatus.INVALID;
		}
	}
	
	@Override
	public String toString()
	{
		return name;
	}
}
