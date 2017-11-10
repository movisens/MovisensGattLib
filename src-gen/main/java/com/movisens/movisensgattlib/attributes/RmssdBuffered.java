package com.movisens.movisensgattlib.attributes;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.movisensgattlib.helper.BufferedAttribute;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class RmssdBuffered extends AbstractReadAttribute implements BufferedAttribute
{

	public static final Characteristic CHARACTERISTIC = MovisensCharacteristics.RMSSD_BUFFERED;
	
	public static final int periodLength = 60;
	private long time;
	private Short rmssd[];
	
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
	
	public Short[] getRmssd()
	{
		return rmssd;
	}
	
	public String getRmssdUnit()
	{
		return "";
	}
	
	public RmssdBuffered(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		
		time = bb.getUint32();
		short numValues = bb.getUint8();
		
		rmssd = new Short[numValues];
		
		for (int i = 0; i < numValues; i++)
		{
			rmssd[i] = bb.getInt16();
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
		for(int i=0; i<rmssd.length; i++)
		{
			result += "Rmssd Buffered: " + "time = " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date((time + (periodLength * i)) * 1000)) + ", " + "rmssd = " + getRmssd()[i] + "\r\n";
		}
		return result;
	}
}
