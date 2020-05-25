package com.movisens.movisensgattlib.helper;

import com.movisens.smartgattlib.helper.AbstractAttribute;
import com.movisens.smartgattlib.helper.Characteristic;


public class BufferedCharacteristic<T extends AbstractAttribute, I extends AbstractData> extends Characteristic<T>
{

    private int bitPosition;
    
    public BufferedCharacteristic(String uuid, String name, int bitPosition, Class<T> attributeClass, Characteristic<?>... requiredCharacteristics)
    {
        super(uuid, name, attributeClass, requiredCharacteristics);
        this.bitPosition = bitPosition;
    }

    public boolean isBitSet(byte[] data)
    {
        return new BitField(data).isBitSet(bitPosition);
    }

}
