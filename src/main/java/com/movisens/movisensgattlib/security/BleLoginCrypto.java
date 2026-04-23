package com.movisens.movisensgattlib.security;

import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.movisens.smartgattlib.helper.GattByteBuffer;

public final class BleLoginCrypto
{
    public static final int CLIENT_NONCE_LEN = 4;
    public static final int SENSOR_CHALLENGE_LEN = 4;
    public static final int PROOF_LEN = 16;
    public static final int PAIRING_CODE_LENGTH = 6;
    public static final int PAIRING_CODE_RADIX = 5;

    private static final byte ROLE_CLIENT = 'C';
    private static final byte ROLE_SENSOR = 'S';
    private static final SecureRandom RANDOM = new SecureRandom();

    private BleLoginCrypto()
    {
    }

    public static byte[] createClientNonce()
    {
        byte[] result = new byte[CLIENT_NONCE_LEN];
        RANDOM.nextBytes(result);
        return result;
    }

    public static long createPairingCodeKey(int[] pairingCodeDigits)
    {
        if (pairingCodeDigits == null)
        {
            throw new IllegalArgumentException("pairing code digits must not be null");
        }
        if (pairingCodeDigits.length != PAIRING_CODE_LENGTH)
        {
            throw new IllegalArgumentException(
                "pairing code must contain exactly " + PAIRING_CODE_LENGTH + " digits"
            );
        }

        long pairingCodeKey = 0;
        for (int digit : pairingCodeDigits)
        {
            if (digit < 0 || digit >= PAIRING_CODE_RADIX)
            {
                throw new IllegalArgumentException(
                    "pairing code digits must be in range 0.." + (PAIRING_CODE_RADIX - 1)
                );
            }
            pairingCodeKey = pairingCodeKey * PAIRING_CODE_RADIX + digit;
        }
        return pairingCodeKey;
    }

    public static byte[] createClientProof(
        long sealingKey,
        byte[] clientPublicKey,
        byte[] sensorPublicKey,
        byte[] sensorChallenge,
        byte[] clientNonce
    ) throws GeneralSecurityException
    {
        return calculateProof(sealingKey, clientPublicKey, sensorPublicKey, sensorChallenge, clientNonce, ROLE_CLIENT);
    }

    public static byte[] createSensorProof(
        long sealingKey,
        byte[] clientPublicKey,
        byte[] sensorPublicKey,
        byte[] sensorChallenge,
        byte[] clientNonce
    ) throws GeneralSecurityException
    {
        return calculateProof(sealingKey, clientPublicKey, sensorPublicKey, sensorChallenge, clientNonce, ROLE_SENSOR);
    }

    private static byte[] calculateProof(
        long sealingKey,
        byte[] clientPublicKey,
        byte[] sensorPublicKey,
        byte[] sensorChallenge,
        byte[] clientNonce,
        byte role
    ) throws GeneralSecurityException
    {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(GattByteBuffer.allocate(8).putInt64(sealingKey).array(), "HmacSHA256"));
        mac.update(clientPublicKey);
        mac.update(sensorPublicKey);
        mac.update(sensorChallenge);
        mac.update(clientNonce);
        mac.update(role);
        return Arrays.copyOf(mac.doFinal(), PROOF_LEN);
    }
}
