package com.movisens.movisensgattlib.helper;

public class BitField
{

    byte[] data;

    BitField(byte[] data)
    {
        this.data = data;
    }

    boolean isBitSet(int bitPosition)
    {
        int bytePosition = bitPosition / 8;
        int bitPositionInByte = bitPosition % 8;

        if (bytePosition < data.length)
        {
            return (data[bytePosition] & (1 << bitPositionInByte)) != 0;
        }
        else
        {
            return false;
        }
    }

    boolean setBit(int bitPosition)
    {
        int bytePosition = bitPosition / 8;
        int bitPositionInByte = bitPosition % 8;

        if (bytePosition < data.length)
        {
            data[bytePosition] = (byte)(1 << bitPositionInByte);
            return true;
        }
        else
        {
            return false;
        }
    }

}
