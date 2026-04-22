package com.movisens.movisensgattlib.security;

import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.movisens.smartgattlib.helper.GattByteBuffer;

public final class BleMitmProofs
{
    public static final int CLIENT_NONCE_LEN = 4;
    public static final int SENSOR_CHALLENGE_LEN = 4;
    public static final int PROOF_LEN = 16;

    private static final byte ROLE_CLIENT = 'C';
    private static final byte ROLE_SENSOR = 'S';
    private static final SecureRandom RANDOM = new SecureRandom();

    private BleMitmProofs()
    {
    }

    public static byte[] createClientNonce()
    {
        byte[] result = new byte[CLIENT_NONCE_LEN];
        RANDOM.nextBytes(result);
        return result;
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
