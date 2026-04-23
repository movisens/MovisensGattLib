package com.movisens.movisensgattlib.security;

import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.interfaces.ECPublicKey;
import java.util.Arrays;

import com.movisens.movisensgattlib.attributes.KeyExchangeRequest1;
import com.movisens.movisensgattlib.attributes.KeyExchangeRequest2;
import com.movisens.smartgattlib.helper.AbstractAttribute;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;

public class KeyExchangeManager
{
    public static final int PUBLIC_KEY_LEN = 33;
    public static final int ATTR_LEN_1 = 20;
    public static final int ATTR_LEN_2 = PUBLIC_KEY_LEN - ATTR_LEN_1;

    public static final int SENSOR_CHALLENGE_LEN = BleLoginCrypto.SENSOR_CHALLENGE_LEN;
   
    KeyPair keyPair;
    private byte[] localPublicKey;
    private byte[] peerPublicKey;
    private byte[] sensorChallenge = new byte[SENSOR_CHALLENGE_LEN];

    private void createLocalKeyPair() throws GeneralSecurityException
    {
        keyPair = Secp256r1Support.generateKeyPair(new SecureRandom());
    }

    public byte[] getLocalPublicKey() throws GeneralSecurityException
    {
        createLocalKeyPair();
        ECPublicKey publicKey = (ECPublicKey) keyPair.getPublic();
        localPublicKey = Secp256r1PointCodec.encodeCompressed(publicKey);
        return cloneBytes(localPublicKey);
    }

    private byte[] calculateSecret(byte[] peerPublicKeyData) throws GeneralSecurityException
    {
        return Secp256r1Support.calculateSecret(keyPair.getPrivate(), peerPublicKeyData);
    }
    
    public byte[] calculateAesKey(byte[] peerPublicKeyData) throws GeneralSecurityException {
       
        byte[] secret = calculateSecret(peerPublicKeyData);
        MessageDigest md = MessageDigest.getInstance("SHA-256");       
        md.update(secret);
        return Arrays.copyOf(md.digest(), 16);
    }
    
    public AbstractAttribute[] getRequestAttributes() throws GeneralSecurityException {
        
        AbstractAttribute[] attributes = new AbstractAttribute[2];
        byte[] publicKey = getLocalPublicKey();
    
        attributes[0] = new KeyExchangeRequest1(Arrays.copyOfRange(publicKey, 0, ATTR_LEN_1));
        attributes[1] = new KeyExchangeRequest2(Arrays.copyOfRange(publicKey, ATTR_LEN_1, PUBLIC_KEY_LEN));
       
        return attributes;
    }
    
    public byte[] getAesKey(AbstractReadAttribute[] response) throws GeneralSecurityException {
        
        peerPublicKey = new byte[PUBLIC_KEY_LEN];
         
        System.arraycopy(response[0].getRawData(), 0, peerPublicKey, 0, ATTR_LEN_1);
        System.arraycopy(response[1].getRawData(), 0, peerPublicKey, ATTR_LEN_1, ATTR_LEN_2);

        byte[] response2 = response[1].getRawData();
        if (response2.length >= ATTR_LEN_2 + SENSOR_CHALLENGE_LEN) {
            System.arraycopy(response2, ATTR_LEN_2, sensorChallenge, 0, SENSOR_CHALLENGE_LEN);
        } else {
            Arrays.fill(sensorChallenge, (byte) 0);
        }

        return calculateAesKey(peerPublicKey);
    }

    public byte[] getClientPublicKey() {
        return cloneBytes(localPublicKey);
    }

    public byte[] getSensorPublicKey() {
        return cloneBytes(peerPublicKey);
    }

    public byte[] getSensorChallenge() {
        return cloneBytes(sensorChallenge);
    }

    public void setSessionContext(byte[] clientPublicKey, byte[] sensorPublicKey, byte[] sensorChallenge) {
        this.localPublicKey = cloneBytes(clientPublicKey);
        this.peerPublicKey = cloneBytes(sensorPublicKey);
        this.sensorChallenge = cloneBytes(sensorChallenge);
    }

    private static byte[] cloneBytes(byte[] value)
    {
        return value == null ? null : Arrays.copyOf(value, value.length);
    }
}
