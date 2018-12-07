package com.movisens.movisensgattlib.helper;

import static org.junit.Assert.*;

import org.junit.Test;

public class BitFieldTest
{

    @Test
    public void testIsBitSet()
    {
        final int arrayLength = 20;
        byte[] data = new byte[arrayLength];
        data[0] = 1;
        data[19] = (byte) 0x80;

        BitField bf = new BitField(data);

        assertTrue(bf.isBitSet(0));
        assertTrue(bf.isBitSet((data.length * 8) - 1));

        for (int i = 1; i < (data.length * 8) - 1; i++)
        {
            assertFalse(bf.isBitSet(i));
        }
    }

    @Test
    public void testSetBit()
    {
        final int arrayLength = 20;

        for (int i = 1; i < arrayLength * 8; i++)
        {
            BitField bf = new BitField(new byte[arrayLength]);

            bf.setBit(i);

            for (int j = 0; j < arrayLength * 8; j++)
            {
                if (i == j)
                {
                    assertTrue(bf.isBitSet(j));
                }
                else
                {
                    assertFalse(bf.isBitSet(j));
                }
            }
        }
    }

}
