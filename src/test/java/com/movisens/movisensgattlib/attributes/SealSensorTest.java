package com.movisens.movisensgattlib.attributes;

import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class SealSensorTest
{

    @Test
    public void test()
    {
        assertFalse(SealSensor.CHARACTERISTIC.isEncryptionAllowed());
    }

}
