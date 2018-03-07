package com.movisens.movisensgattlib.helper;

import com.movisens.smartgattlib.helper.AbstractAttribute;
import com.movisens.smartgattlib.helper.Characteristic;


public class BufferedCharacteristic<T extends AbstractAttribute, I extends AbstractData> extends Characteristic<T>
{

    public BufferedCharacteristic(String uuid, String name, Class<T> attributeClass)
    {
        super(uuid, name, attributeClass);
    }

}
