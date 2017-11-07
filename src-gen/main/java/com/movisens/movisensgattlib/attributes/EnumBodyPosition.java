package com.movisens.movisensgattlib.attributes;

public enum EnumBodyPosition
{
	UNKNOWN                       ((short)0, "unknown"),
	LYING_SUPINE                  ((short)1, "lying_supine"),
	LYING_LEFT                    ((short)2, "lying_left"),
	LYING_PRONE                   ((short)3, "lying_prone"),
	LYING_RIGHT                   ((short)4, "lying_right"),
	UPRIGHT                       ((short)5, "upright"),
	SITTING_LYING                 ((short)6, "sitting_lying"),
	STANDING                      ((short)7, "standing"),
	NOT_WORN                      ((short)99, "not_worn"),
	INVALID                       ((short)100, "invalid");

	private final short value;
	private final String name;

	EnumBodyPosition(short value, String name)
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

	public static EnumBodyPosition getByValue(short value)
	{
		switch (value)
		{
			case 0:
				return EnumBodyPosition.UNKNOWN;
			case 1:
				return EnumBodyPosition.LYING_SUPINE;
			case 2:
				return EnumBodyPosition.LYING_LEFT;
			case 3:
				return EnumBodyPosition.LYING_PRONE;
			case 4:
				return EnumBodyPosition.LYING_RIGHT;
			case 5:
				return EnumBodyPosition.UPRIGHT;
			case 6:
				return EnumBodyPosition.SITTING_LYING;
			case 7:
				return EnumBodyPosition.STANDING;
			case 99:
				return EnumBodyPosition.NOT_WORN;
			default:
				return EnumBodyPosition.INVALID;
		}
	}
	
	@Override
	public String toString()
	{
		return name;
	}
}
