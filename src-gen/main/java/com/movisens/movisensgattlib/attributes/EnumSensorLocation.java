package com.movisens.movisensgattlib.attributes;

public enum EnumSensorLocation
{
	RIGHT_SIDE_HIP                ((short)1, "right_side_hip"),
	CHEST                         ((short)2, "chest"),
	RIGHT_WRIST                   ((short)3, "right_wrist"),
	LEFT_WRIST                    ((short)4, "left_wrist"),
	LEFT_ANKLE                    ((short)5, "left_ankle"),
	RIGHT_ANKLE                   ((short)6, "right_ankle"),
	RIGHT_THIGH                   ((short)7, "right_thigh"),
	LEFT_THIGH                    ((short)8, "left_thigh"),
	RIGHT_UPPER_ARM               ((short)9, "right_upper_arm"),
	LEFT_UPPER_ARM                ((short)10, "left_upper_arm"),
	LEFT_SIDE_HIP                 ((short)11, "left_side_hip"),
	INVALID                       ((short)12, "invalid");

	private final short value;
	private final String name;

	EnumSensorLocation(short value, String name)
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

	public static EnumSensorLocation getByValue(short value)
	{
		switch (value)
		{
			case 1:
				return EnumSensorLocation.RIGHT_SIDE_HIP;
			case 2:
				return EnumSensorLocation.CHEST;
			case 3:
				return EnumSensorLocation.RIGHT_WRIST;
			case 4:
				return EnumSensorLocation.LEFT_WRIST;
			case 5:
				return EnumSensorLocation.LEFT_ANKLE;
			case 6:
				return EnumSensorLocation.RIGHT_ANKLE;
			case 7:
				return EnumSensorLocation.RIGHT_THIGH;
			case 8:
				return EnumSensorLocation.LEFT_THIGH;
			case 9:
				return EnumSensorLocation.RIGHT_UPPER_ARM;
			case 10:
				return EnumSensorLocation.LEFT_UPPER_ARM;
			case 11:
				return EnumSensorLocation.LEFT_SIDE_HIP;
			default:
				return EnumSensorLocation.INVALID;
		}
	}
	
	@Override
	public String toString()
	{
		return name;
	}
}
