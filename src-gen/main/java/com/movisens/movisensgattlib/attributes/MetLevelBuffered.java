package com.movisens.movisensgattlib.attributes;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.movisensgattlib.helper.BufferedAttribute;
import com.movisens.smartgattlib.helper.AbstractAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class MetLevelBuffered extends AbstractAttribute implements BufferedAttribute
{

	public static final Characteristic CHARACTERISTIC = MovisensCharacteristics.MET_LEVEL_BUFFERED;
	
	public static final int periodLength = 60;
	private long time;
	private Short sedentary[];
	private Short light[];
	private Short moderate[];
	private Short vigorous[];
	
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
		String[] names = {"sedentary", "light", "moderate", "vigorous"};
		return names;
	}

	@Override
	public String[] getValueUnits()
	{
		String[] names = {"", "", "", ""};
		return names;
	}
	
	@Override
	public double[][] getValues()
	{
		int numSamples = sedentary.length;
		double[][] data = new double[numSamples][4];
		
		for(int i=0; i<numSamples; i++)
		{
			data[i][0] = sedentary[i];
			data[i][1] = light[i];
			data[i][2] = moderate[i];
			data[i][3] = vigorous[i];
		}
		
		return data;
	}

	public Short[] getSedentary()
	{
		return sedentary;
	}
	
	public String getSedentaryUnit()
	{
		return "";
	}
	
	public Short[] getLight()
	{
		return light;
	}
	
	public String getLightUnit()
	{
		return "";
	}
	
	public Short[] getModerate()
	{
		return moderate;
	}
	
	public String getModerateUnit()
	{
		return "";
	}
	
	public Short[] getVigorous()
	{
		return vigorous;
	}
	
	public String getVigorousUnit()
	{
		return "";
	}
	
	public MetLevelBuffered(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		
		time = bb.getUint32();
		short numValues = bb.getUint8();
		
		sedentary = new Short[numValues];
		light = new Short[numValues];
		moderate = new Short[numValues];
		vigorous = new Short[numValues];
		
		for (int i = 0; i < numValues; i++)
		{
			sedentary[i] = bb.getUint8();
			light[i] = bb.getUint8();
			moderate[i] = bb.getUint8();
			vigorous[i] = bb.getUint8();
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
		for(int i=0; i<sedentary.length; i++)
		{
			result += "time = " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date((time + (periodLength * i)) * 1000)) + ", " + "sedentary = " + getSedentary()[i] + ", " + "light = " + getLight()[i] + ", " + "moderate = " + getModerate()[i] + ", " + "vigorous = " + getVigorous()[i] + " \r\n";
		}
		return result;
	}
}
