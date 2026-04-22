package com.movisens.movisensgattlib.security;

import static org.junit.Assert.assertArrayEquals;

import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.junit.Test;

import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.helper.Characteristic;

public class KeyExchangeManagerVectorsTest
{
    private static final byte[] LOCAL_PRIVATE_KEY_PKCS8 = hex(
        "3041020100301306072a8648ce3d020106082a8648ce3d030107042730250201010420e2b2966b7b883c82db8ccfa61ae4901800ae93da9f3a8434f41ab17ce696953d"
    );

    private static final byte[] LOCAL_PUBLIC_KEY_X509 = hex(
        "3059301306072a8648ce3d020106082a8648ce3d0301070342000459b9bdc01d06917fc85e6a438c94c01b7c908ce7ef28752f168b78568a657136143e280a6b9a3a2a90421ced2e3afed014adb0ca8ddb952d849b3ce070b70d90"
    );

    private static final byte[] LOCAL_PUBLIC_KEY_COMPRESSED = hex(
        "0259b9bdc01d06917fc85e6a438c94c01b7c908ce7ef28752f168b78568a657136"
    );

    private static final byte[] PEER_PRIVATE_KEY_PKCS8 = hex(
        "3041020100301306072a8648ce3d020106082a8648ce3d0301070427302502010104207ca82bc1a2d7410c40f3f1c6af7ba9c107f0d86f35dfcdf154828cd79bd9ce05"
    );

    private static final byte[] PEER_PUBLIC_KEY_COMPRESSED = hex(
        "02e107e9e411e2cee6cbeff3015d39d62818ac75b384c2ec78148ba5f7e587b496"
    );

    private static final byte[] EXPECTED_SECRET = hex(
        "e35f8b5fbdec9e747b0fee6b1297b0f0ec39ea259bc2ee8431a14aef4554697d"
    );

    private static final byte[] EXPECTED_AES_KEY = hex(
        "0bc5f0a00163bd01727d635d6c399112"
    );

    private static final byte[] SENSOR_CHALLENGE = new byte[] { 1, 2, 3, 4 };

    @Test
    public void calculateSecretShouldMatchFixedVector() throws Exception
    {
        KeyPair localKeyPair = createKeyPair(LOCAL_PRIVATE_KEY_PKCS8, LOCAL_PUBLIC_KEY_X509);
        byte[] secret = Secp256r1Support.calculateSecret(localKeyPair.getPrivate(), PEER_PUBLIC_KEY_COMPRESSED);
        assertArrayEquals(EXPECTED_SECRET, secret);
    }

    @Test
    public void calculateSecretShouldBeSymmetricForFixedVector() throws Exception
    {
        KeyFactory keyFactory = KeyFactory.getInstance("EC");
        ECPrivateKey peerPrivateKey = (ECPrivateKey) keyFactory.generatePrivate(new PKCS8EncodedKeySpec(PEER_PRIVATE_KEY_PKCS8));

        byte[] secret = Secp256r1Support.calculateSecret(peerPrivateKey, LOCAL_PUBLIC_KEY_COMPRESSED);
        assertArrayEquals(EXPECTED_SECRET, secret);
    }

    @Test
    public void calculateAesKeyShouldMatchFixedVector() throws Exception
    {
        KeyExchangeManager manager = new KeyExchangeManager();
        manager.keyPair = createKeyPair(LOCAL_PRIVATE_KEY_PKCS8, LOCAL_PUBLIC_KEY_X509);

        assertArrayEquals(EXPECTED_AES_KEY, manager.calculateAesKey(PEER_PUBLIC_KEY_COMPRESSED));
    }

    @Test
    public void getAesKeyShouldAssemblePeerKeyAndChallenge() throws Exception
    {
        KeyExchangeManager manager = new KeyExchangeManager();
        manager.keyPair = createKeyPair(LOCAL_PRIVATE_KEY_PKCS8, LOCAL_PUBLIC_KEY_X509);

        AbstractReadAttribute[] response = new AbstractReadAttribute[] {
            new RawReadAttribute(slice(PEER_PUBLIC_KEY_COMPRESSED, 0, KeyExchangeManager.ATTR_LEN_1)),
            new RawReadAttribute(join(
                slice(PEER_PUBLIC_KEY_COMPRESSED, KeyExchangeManager.ATTR_LEN_1, KeyExchangeManager.PUBLIC_KEY_LEN),
                SENSOR_CHALLENGE
            ))
        };

        assertArrayEquals(EXPECTED_AES_KEY, manager.getAesKey(response));
        assertArrayEquals(PEER_PUBLIC_KEY_COMPRESSED, manager.getSensorPublicKey());
        assertArrayEquals(SENSOR_CHALLENGE, manager.getSensorChallenge());
    }

    private static KeyPair createKeyPair(byte[] privateKeyPkcs8, byte[] publicKeyX509) throws GeneralSecurityException
    {
        KeyFactory keyFactory = KeyFactory.getInstance("EC");
        ECPublicKey publicKey = (ECPublicKey) keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyX509));
        ECPrivateKey privateKey = (ECPrivateKey) keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKeyPkcs8));
        return new KeyPair(publicKey, privateKey);
    }

    private static byte[] slice(byte[] value, int from, int to)
    {
        byte[] result = new byte[to - from];
        System.arraycopy(value, from, result, 0, result.length);
        return result;
    }

    private static byte[] join(byte[] left, byte[] right)
    {
        byte[] result = new byte[left.length + right.length];
        System.arraycopy(left, 0, result, 0, left.length);
        System.arraycopy(right, 0, result, left.length, right.length);
        return result;
    }

    private static byte[] hex(String value)
    {
        byte[] result = new byte[value.length() / 2];

        for (int i = 0; i < result.length; i++) {
            int index = i * 2;
            result[i] = (byte) Integer.parseInt(value.substring(index, index + 2), 16);
        }

        return result;
    }

    private static final class RawReadAttribute extends AbstractReadAttribute
    {
        private RawReadAttribute(byte[] rawData)
        {
            this.data = rawData;
        }

        @Override
        public Characteristic<? extends com.movisens.smartgattlib.helper.AbstractAttribute> getCharacteristic()
        {
            return null;
        }
    }
}
