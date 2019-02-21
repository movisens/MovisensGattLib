package com.movisens.movisensgattlib.attributes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.movisensgattlib.helper.AbstractBufferedAttribute;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class LightRgbBuffered extends AbstractBufferedAttribute<LightRgbData>
{

	public static final BufferedCharacteristic<LightRgbBuffered, LightRgbData> CHARACTERISTIC = MovisensCharacteristics.LIGHT_RGB_BUFFERED;
	
	public static final int periodLength = 60;
	private long time;
	private Long red[];
	private Long green[];
	private Long blue[];
	
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

	@Override
	public String[] getValueNames()
	{
		String[] names = {"red", "green", "blue"};
		return names;
	}

	@Override
	public String[] getValueUnits()
	{
		String[] names = {"", "", ""};
		return names;
	}
	
	@Override
	public double[][] getValues()
	{
		int numSamples = red.length;
		double[][] data = new double[numSamples][3];
		
		for(int i=0; i<numSamples; i++)
		{
			data[i][0] = red[i];
			data[i][1] = green[i];
			data[i][2] = blue[i];
		}
		
		return data;
	}

	public Long[] getRed()
	{
		return red;
	}
	
	public String getRedUnit()
	{
		return "";
	}
	
	public Long[] getGreen()
	{
		return green;
	}
	
	public String getGreenUnit()
	{
		return "";
	}
	
	public Long[] getBlue()
	{
		return blue;
	}
	
	public String getBlueUnit()
	{
		return "";
	}
	
	public LightRgbBuffered(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		
		time = bb.getUint32();
		short numValues = bb.getUint8();
		
		red = new Long[numValues];
		green = new Long[numValues];
		blue = new Long[numValues];
		
		for (int i = 0; i < numValues; i++)
		{
			red[i] = bb.getUint32();
			green[i] = bb.getUint32();
			blue[i] = bb.getUint32();
		}
	}

	@Override
	public BufferedCharacteristic<LightRgbBuffered, LightRgbData> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		String result = "";
		for(int i=0; i<red.length; i++)
		{
			result += "time = " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date((time + (periodLength * i)) * 1000)) + ", " + "red = " + getRed()[i].toString() + ", " + "green = " + getGreen()[i].toString() + ", " + "blue = " + getBlue()[i].toString() + " \r\n";
		}
		return result;
	}

	@Override
	public Iterable<LightRgbData> getData()
	{
	    Vector<LightRgbData> datas = new Vector<LightRgbData>();
	    long now = new Date().getTime();
	    
	    for(int i=0; i<red.length; i++)
	    {
	        datas.add(new LightRgbData(now, (time + (periodLength * i)) * 1000, periodLength, getRed()[i], getGreen()[i], getBlue()[i]));
	    }
	    
	    return datas;
	}
}
