package com.movisens.movisensgattlib.helper;

import com.movisens.smartgattlib.helper.AbstractReadAttribute;

public abstract class AbstractBufferedAttribute<T extends AbstractData> extends AbstractReadAttribute implements BufferedAttribute<T>
{
    public boolean isBitSet(byte[] data)
    {
        return new BitField(data).isBitSet(getBitPosition());
    }

}
