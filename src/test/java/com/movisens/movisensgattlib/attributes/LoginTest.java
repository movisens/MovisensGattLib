package com.movisens.movisensgattlib.attributes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.security.NoSuchAlgorithmException;

import org.junit.Test;

import com.movisens.smartgattlib.helper.GattByteBuffer;
import com.movisens.smartgattlib.security.CryptoManager;

public class LoginTest
{

    @Test
    public void test() throws NoSuchAlgorithmException
    {
        CryptoManager cryptoManager = new CryptoManager();
        
        byte[] secretKey = new byte[16];
        cryptoManager.setKey(secretKey);
        
        Login login = new Login(cryptoManager, "secret");

        byte[] data = login.getOutgoingData(cryptoManager);

        byte[] plainText = cryptoManager.processAfterReceive(data);
        GattByteBuffer bb = GattByteBuffer.wrap(plainText);
        assertEquals(bb.getInt64().longValue(), login.getKey()[0]);
        
        assertTrue(Login.CHARACTERISTIC.isEncryptionAllowed());
    }

}
