package com.movisens.movisensgattlib.attributes;

import static org.junit.Assert.*;

import java.security.NoSuchAlgorithmException;

import org.junit.Test;

import com.movisens.smartgattlib.helper.GattByteBuffer;
import com.movisens.smartgattlib.security.CryptoManagerProvider;

public class LoginTest
{

    @Test
    public void test() throws NoSuchAlgorithmException
    {
        
        Login login = new Login("secret");

        byte[] data = login.getOutgoingData();

        byte[] plainText = CryptoManagerProvider.get().processAfterReceive(data);
        GattByteBuffer bb = GattByteBuffer.wrap(plainText);
        assertEquals(bb.getInt64().longValue(), login.getKey()[0]);
        
        assertTrue(Login.CHARACTERISTIC.isEncryptionAllowed());
    }

}
