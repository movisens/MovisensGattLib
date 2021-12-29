package com.movisens.movisensgattlib.attributes;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.security.CryptoManager;


public class AgeFloatTest
{

    @Test
    public void test()
    {
        CryptoManager cryptoManager = new CryptoManager();
        
        double ageOutput = 13.2;
        AgeFloat ageFloatOutput = new AgeFloat(ageOutput);
        
        AgeFloat ageFloatInput = (AgeFloat) MovisensCharacteristics.AGE_FLOAT.createAttribute(cryptoManager, ageFloatOutput.getOutgoingData(cryptoManager));
        
        assertEquals(ageOutput, ageFloatInput.getAge().doubleValue(), 0.00001);
    }

}
