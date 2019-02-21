package com.movisens.movisensgattlib.attributes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.movisensgattlib.helper.AbstractBufferedAttribute;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class MetLevelBuffered extends AbstractBufferedAttribute<MetLevelData>
{

	public static final BufferedCharacteristic<MetLevelBuffered, MetLevelData> CHARACTERISTIC = MovisensCharacteristics.MET_LEVEL_BUFFERED;
	
	public static final int periodLength = 60;
	private long time;
	private Double sedentary[];
	private Double light[];
	private Double moderate[];
	private Double vigorous[];
	
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
		String[] names = {"s", "s", "s", "s"};
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

	public Double[] getSedentary()
	{
		return sedentary;
	}
	
	public String getSedentaryUnit()
	{
		return "s";
	}
	
	public Double[] getLight()
	{
		return light;
	}
	
	public String getLightUnit()
	{
		return "s";
	}
	
	public Double[] getModerate()
	{
		return moderate;
	}
	
	public String getModerateUnit()
	{
		return "s";
	}
	
	public Double[] getVigorous()
	{
		return vigorous;
	}
	
	public String getVigorousUnit()
	{
		return "s";
	}
	
	public MetLevelBuffered(byte[] data)
	{
		this.data = data;
		GattByteBuffer bb = GattByteBuffer.wrap(data);
		
		time = bb.getUint32();
		short numValues = bb.getUint8();
		
		sedentary = new Double[numValues];
		light = new Double[numValues];
		moderate = new Double[numValues];
		vigorous = new Double[numValues];
		
		for (int i = 0; i < numValues; i++)
		{
			sedentary[i] = new Double(bb.getUint8());
			light[i] = new Double(bb.getUint8());
			moderate[i] = new Double(bb.getUint8());
			vigorous[i] = new Double(bb.getUint8());
		}
	}

	@Override
	public BufferedCharacteristic<MetLevelBuffered, MetLevelData> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		String result = "";
		for(int i=0; i<sedentary.length; i++)
		{
			result += "time = " + new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date((time + (periodLength * i)) * 1000)) + ", " + "sedentary = " + getSedentary()[i].toString() + getSedentaryUnit() + ", " + "light = " + getLight()[i].toString() + getLightUnit() + ", " + "moderate = " + getModerate()[i].toString() + getModerateUnit() + ", " + "vigorous = " + getVigorous()[i].toString() + getVigorousUnit() + " \r\n";
		}
		return result;
	}

	@Override
	public Iterable<MetLevelData> getData()
	{
	    Vector<MetLevelData> datas = new Vector<MetLevelData>();
	    long now = new Date().getTime();
	    
	    for(int i=0; i<sedentary.length; i++)
	    {
	        datas.add(new MetLevelData(now, (time + (periodLength * i)) * 1000, periodLength, getSedentary()[i], getLight()[i], getModerate()[i], getVigorous()[i]));
	    }
	    
	    return datas;
	}
}
