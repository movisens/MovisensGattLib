package com.movisens.movisensgattlib;

import com.movisens.movisensgattlib.characteristics.AgeFloat;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class AgeFloatTest {
    @Test
    public void testAgeFloat() {
        Float testAge = 12.5F;
        AgeFloat ageFloat = new AgeFloat(testAge);
        AgeFloat converted = new AgeFloat(ageFloat.getBytes());
        assertTrue(converted.getValue() == testAge);
    }
}
