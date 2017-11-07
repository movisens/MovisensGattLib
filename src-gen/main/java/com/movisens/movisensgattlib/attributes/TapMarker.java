package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class TapMarker extends AbstractReadAttribute
{

	public static final Characteristic CHARACTERISTIC = MovisensCharacteristics.TAP_MARKER;
	
	private Long tapMarker;
	
	public Long getTapMarker()
	{
		return tapMarker;
	}
	
	public String getTapMarkerUnit()
	{
		return "";
	}
	
	public TapMarker(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		tapMarker = bb.getUint32();
	}

	@Override
	public Characteristic getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		return "Tap Marker: " + "tapMarker = " + getTapMarker() + " " + getTapMarkerUnit();
	}
}
