package com.movisens.movisensgattlib.attributes;

import static org.junit.Assert.*;

import org.junit.Test;

import com.movisens.movisensdevgattlib.security.CryptoManagerProvider;


public class AgeFloatTest
{

    @Test
    public void test()
    {
        CryptoManagerProvider.get().setPassword("secret");
        
        double ageOutput = 13.2;
        AgeFloat ageFloatOutput = new AgeFloat(ageOutput);
        
        AgeFloat ageFloatInput = new AgeFloat(ageFloatOutput.getBytes());
        
        assertEquals(ageOutput, ageFloatInput.getAge().doubleValue(), 0.00001);
    }

}
