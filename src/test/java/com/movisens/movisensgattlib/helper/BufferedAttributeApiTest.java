package com.movisens.movisensgattlib.helper;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.movisens.movisensgattlib.attributes.BatteryLevelBuffered;
import com.movisens.movisensgattlib.attributes.BatteryLevelData;

public class BufferedAttributeApiTest
{
    @Test
    @SuppressWarnings("deprecation")
    public void exposesCorrectedDataAccessorAliases()
    {
        BatteryLevelData data = new BatteryLevelData(123L, 456L, 60, 10.0);

        assertEquals(123L, data.getArrivalTime());
        assertEquals(data.getArrivalTime(), data.getArivalTime());
        assertEquals(60, data.getPeriodLength());
        assertEquals(data.getPeriodLength(), data.getPeriodlength());
    }

    @Test
    @SuppressWarnings("deprecation")
    public void exposesCorrectedSampleRateAlias()
    {
        BatteryLevelBuffered attribute = new BatteryLevelBuffered(new byte[] {0, 0, 0, 0, 0});

        assertEquals(attribute.getSampleRate(), attribute.getSamplerate(), 0.0);
        assertEquals(1.0 / BatteryLevelBuffered.periodLength, attribute.getSampleRate(), 0.0);
    }
}
