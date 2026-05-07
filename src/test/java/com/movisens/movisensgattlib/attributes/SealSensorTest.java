package com.movisens.movisensgattlib.attributes;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class SealSensorTest
{

    /**
     * Since 588940a ("SealSensor no longer plain text") SealSensor must travel
     * over an encrypted link — it no longer implements PlainTextAttribute, so
     * Characteristic#isEncryptionAllowed() returns true.
     */
    @Test
    public void test()
    {
        assertTrue(SealSensor.CHARACTERISTIC.isEncryptionAllowed());
    }

}
