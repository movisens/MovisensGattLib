package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class Ecg extends AbstractReadAttribute
{
	public static final Characteristic CHARACTERISTIC = MovisensCharacteristics.ECG;
	
    private int values[];
	
	public Ecg(byte[] data)
	{
		this.data = data;
		GattByteBuffer byteBuffer = GattByteBuffer.wrap(data);
        values = new int[19];
        
        values[0] = byteBuffer.getInt16();
        int lastValue = values[0] ;
        
        for(int i=0;i<18;i++)
        {
            values[i+1] = lastValue - byteBuffer.getInt8();
            lastValue = values[i+1] ;
        }
	}

	public int[] getValues()
	{
	    return values;
	}
	
	@Override
	public Characteristic getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
        String result = "Ecg\r\n";
        for(int i=0;i<19;i++)
        {
            result += values[i] + " ";
        }
        
        return result;
	}
}
