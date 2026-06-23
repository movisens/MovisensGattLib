package com.movisens.movisensgattlib.attributes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.junit.Test;

import com.movisens.movisensgattlib.security.BleLoginCrypto;
import com.movisens.movisensgattlib.security.KeyExchangeManager;
import com.movisens.smartgattlib.helper.GattByteBuffer;
import com.movisens.smartgattlib.security.CryptoManager;

// Login ist @Deprecated; LoginTest deckt den Altpfad ab, bis Login mit dem Altpfad gelöscht wird.
@SuppressWarnings("deprecation")
public class LoginTest
{

    @Test
    public void test() throws NoSuchAlgorithmException
    {
        CryptoManager cryptoManager = new CryptoManager();
        
        byte[] secretKey = new byte[16];
        cryptoManager.setKey(secretKey);

        KeyExchangeManager keyExchangeManager = new KeyExchangeManager();
        keyExchangeManager.setSessionContext(
                new byte[KeyExchangeManager.PUBLIC_KEY_LEN],
                new byte[KeyExchangeManager.PUBLIC_KEY_LEN],
                new byte[] { 1, 2, 3, 4 });
        
        Login login = new Login(cryptoManager, keyExchangeManager, "secret");

        byte[] data = login.getOutgoingData(cryptoManager);

        byte[] plainText = cryptoManager.processAfterReceive(data);
        GattByteBuffer bb = GattByteBuffer.wrap(plainText);
        byte[] nonce = new byte[4];
        byte[] proof = new byte[16];
        bb.getInt8(nonce, 0, nonce.length);
        bb.getInt8(proof, 0, proof.length);
        assertTrue(Arrays.equals(nonce, login.getClientNonce()));
        assertTrue(Arrays.equals(proof, login.getClientProof()));
        
        assertTrue(Login.CHARACTERISTIC.isEncryptionAllowed());
    }

    @Test
    public void testPairingCodeConstructor() throws Exception
    {
        CryptoManager cryptoManager = new CryptoManager();

        byte[] secretKey = new byte[16];
        cryptoManager.setKey(secretKey);

        KeyExchangeManager keyExchangeManager = new KeyExchangeManager();
        keyExchangeManager.setSessionContext(
            new byte[KeyExchangeManager.PUBLIC_KEY_LEN],
            new byte[KeyExchangeManager.PUBLIC_KEY_LEN],
            new byte[] { 1, 2, 3, 4 }
        );

        Login login = new Login(cryptoManager, keyExchangeManager, new int[] { 0, 0, 0, 4, 4, 3 });

        byte[] data = login.getOutgoingData(cryptoManager);

        byte[] plainText = cryptoManager.processAfterReceive(data);
        GattByteBuffer bb = GattByteBuffer.wrap(plainText);
        byte[] nonce = new byte[4];
        byte[] proof = new byte[16];
        bb.getInt8(nonce, 0, nonce.length);
        bb.getInt8(proof, 0, proof.length);

        assertEquals(123L, login.getKey());
        assertTrue(Arrays.equals(nonce, login.getClientNonce()));
        assertTrue(Arrays.equals(proof, login.getClientProof()));
        assertTrue(login.isAuthConfirmValid(
            BleLoginCrypto.createSensorProof(
                123L,
                keyExchangeManager.getClientPublicKey(),
                keyExchangeManager.getSensorPublicKey(),
                keyExchangeManager.getSensorChallenge(),
                login.getClientNonce()
            )
        ));
    }

}
