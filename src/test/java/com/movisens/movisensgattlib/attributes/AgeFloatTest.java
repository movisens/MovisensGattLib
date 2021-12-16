package com.movisens.movisensgattlib.attributes;

import static org.junit.Assert.*;

import org.junit.Test;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.security.CryptoManagerProvider;


public class AgeFloatTest
{

    @Test
    public void test()
    {
        CryptoManagerProvider.get().setPassword("secret");
        
        double ageOutput = 13.2;
        AgeFloat ageFloatOutput = new AgeFloat(ageOutput);
        
        AgeFloat ageFloatInput = (AgeFloat) MovisensCharacteristics.AGE_FLOAT.createAttribute(ageFloatOutput.getOutgoingData());
        
        assertEquals(ageOutput, ageFloatInput.getAge().doubleValue(), 0.00001);
    }

}
