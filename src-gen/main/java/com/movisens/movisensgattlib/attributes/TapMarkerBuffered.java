package com.movisens.movisensgattlib.attributes;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.movisensgattlib.helper.BufferedAttribute;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class TapMarkerBuffered extends AbstractReadAttribute implements BufferedAttribute
{

	public static final Characteristic CHARACTERISTIC = MovisensCharacteristics.TAP_MARKER_BUFFERED;
	
	public static final int periodLength = 60;
	private long time;
	private Long tapMarker[];
	
	@Override
	public Date getTime()
	{
		return new Date(time*1000);
	}
	
	@Override
	public double getSamplerate()
	{
		return 1.0/periodLength;
	}
	
	public Long[] getTapMarker()
	{
		return tapMarker;
	}
	
	public String getTapMarkerUnit()
	{
		return "";
	}
	
	public TapMarkerBuffered(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		
		time = bb.getUint32();
		short numValues = bb.getUint8();
		
		tapMarker = new Long[numValues];
		
		for (int i = 0; i < numValues; i++)
		{
			tapMarker[i] = bb.getUint32();
		}
	}

	@Override
	public Characteristic getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		String result = "";
		for(int i=0; i<tapMarker.length; i++)
		{
			result += "Tap Marker Buffered: " + "time = " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date((time + (periodLength * i)) * 1000)) + ", " + "tapMarker = " + getTapMarker()[i] + "\r\n";
		}
		return result;
	}
}
