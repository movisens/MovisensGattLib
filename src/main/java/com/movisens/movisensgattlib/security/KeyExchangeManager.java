package com.movisens.movisensgattlib.security;

import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.ECGenParameterSpec;

import javax.crypto.KeyAgreement;

import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.interfaces.ECPublicKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.jce.spec.ECPublicKeySpec;
import org.bouncycastle.util.Arrays;

import com.movisens.movisensgattlib.attributes.KeyExchangeRequest1;
import com.movisens.movisensgattlib.attributes.KeyExchangeRequest2;
import com.movisens.smartgattlib.helper.AbstractAttribute;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;

public class KeyExchangeManager
{

    //final static String CURVE = "SECP192R1";
    //public final static int PUBLIC_KEY_LEN = 25;
    
    final static String CURVE = "SECP256R1";
    public final static int PUBLIC_KEY_LEN = 33;
    
    public static final int ATTR_LEN_1 = 20;
    public static final int ATTR_LEN_2 = PUBLIC_KEY_LEN - ATTR_LEN_1;

    public static final int SENSOR_CHALLENGE_LEN = BleMitmProofs.SENSOR_CHALLENGE_LEN;
   
    KeyPair keyPair;
    private byte[] localPublicKey;
    private byte[] peerPublicKey;
    private byte[] sensorChallenge = new byte[SENSOR_CHALLENGE_LEN];

    static
    {
        Security.addProvider(new BouncyCastleProvider());
    }

    void createLocalKeyPair() throws GeneralSecurityException
    {
        KeyPairGenerator kpgen = KeyPairGenerator.getInstance("ECDH", "BC");
        kpgen.initialize(new ECGenParameterSpec(CURVE), new SecureRandom());
        keyPair = kpgen.generateKeyPair();
    }

    public byte[] getLocalPublicKey() throws GeneralSecurityException
    {
        createLocalKeyPair();
        ECPublicKey eckey = (ECPublicKey) keyPair.getPublic();
        localPublicKey = eckey.getQ().getEncoded(true);
        return Arrays.clone(localPublicKey);
    }

    private byte[] calculateSecret(byte[] peerPublicKeyData) throws GeneralSecurityException
    {
        ECParameterSpec params = ECNamedCurveTable.getParameterSpec(CURVE);
        ECPublicKeySpec pubKey = new ECPublicKeySpec(params.getCurve().decodePoint(peerPublicKeyData), params);
        
        KeyFactory kf = KeyFactory.getInstance("ECDH", "BC");
        PublicKey  peerPublicKey = kf.generatePublic(pubKey);

        KeyAgreement ka = KeyAgreement.getInstance("ECDH", "BC");
        ka.init(keyPair.getPrivate());
        ka.doPhase(peerPublicKey, true);
        
        return ka.generateSecret();
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
        
        peerPublicKey = new byte[KeyExchangeManager.PUBLIC_KEY_LEN];
         
        System.arraycopy(response[0].getRawData(), 0, peerPublicKey, 0, ATTR_LEN_1);
        System.arraycopy(response[1].getRawData(), 0, peerPublicKey, ATTR_LEN_1, ATTR_LEN_2);

        byte[] response2 = response[1].getRawData();
        if (response2.length >= ATTR_LEN_2 + SENSOR_CHALLENGE_LEN) {
            System.arraycopy(response2, ATTR_LEN_2, sensorChallenge, 0, SENSOR_CHALLENGE_LEN);
        } else {
            java.util.Arrays.fill(sensorChallenge, (byte) 0);
        }

        return calculateAesKey(peerPublicKey);
    }

    public byte[] getClientPublicKey() {
        return Arrays.clone(localPublicKey);
    }

    public byte[] getSensorPublicKey() {
        return Arrays.clone(peerPublicKey);
    }

    public byte[] getSensorChallenge() {
        return Arrays.clone(sensorChallenge);
    }

    public void setSessionContext(byte[] clientPublicKey, byte[] sensorPublicKey, byte[] sensorChallenge) {
        this.localPublicKey = Arrays.clone(clientPublicKey);
        this.peerPublicKey = Arrays.clone(sensorPublicKey);
        this.sensorChallenge = Arrays.clone(sensorChallenge);
    }
}
