package com.movisens.movisensgattlib.attributes;

import java.security.GeneralSecurityException;
import java.util.Arrays;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.movisensgattlib.security.BleLoginCrypto;
import com.movisens.movisensgattlib.security.KeyExchangeManager;
import com.movisens.smartgattlib.helper.AbstractWriteAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;
import com.movisens.smartgattlib.security.CryptoManager;
import com.movisens.smartgattlib.security.KeyGenerator;

public class Login extends AbstractWriteAttribute
{

    public static final Characteristic<Login> CHARACTERISTIC = MovisensCharacteristics.LOGIN;

    private long key;
    private byte[] clientNonce;
    private byte[] clientProof;
    private byte[] expectedSensorProof;

    public long getKey()
    {
        return key;
    }

    public byte[] getClientNonce()
    {
        return Arrays.copyOf(clientNonce, clientNonce.length);
    }

    public byte[] getClientProof()
    {
        return Arrays.copyOf(clientProof, clientProof.length);
    }

    public byte[] getExpectedSensorProof()
    {
        return Arrays.copyOf(expectedSensorProof, expectedSensorProof.length);
    }

    public boolean isAuthConfirmValid(byte[] authConfirm)
    {
        return Arrays.equals(expectedSensorProof, authConfirm);
    }

    /**
     * Creates a BLE login write from the temporary 6-digit LED pairing code.
     *
     * <p>The pairing code must contain exactly 6 digits and each digit must be in the range {@code 0..4}.
     * The constructor derives the temporary pairing key from those digits and builds the encrypted
     * {@code LOGIN} payload for the current key-exchange session.</p>
     *
     * <p>Color mapping: {@code 0=red}, {@code 1=green}, {@code 2=blue}, {@code 3=white},
     * {@code 4=yellow}.</p>
     *
     * @param cryptoManager active BLE crypto context; encryption must already be enabled
     * @param keyExchangeManager completed BLE key-exchange context for the current session
     * @param pairingCodeDigits pairing color code as 6 digits in the range {@code 0..4}
     * @throws RuntimeException if encryption is not enabled or the login proof cannot be created
     */
    public Login(CryptoManager cryptoManager, KeyExchangeManager keyExchangeManager, int[] pairingCodeDigits)
    {
        if (cryptoManager.encryptionEnabled())
        {
            try
            {
                long pairingCodeKey = BleLoginCrypto.createPairingCodeKey(pairingCodeDigits);
                initialize(
                    pairingCodeKey,
                    keyExchangeManager.getClientPublicKey(),
                    keyExchangeManager.getSensorPublicKey(),
                    keyExchangeManager.getSensorChallenge()
                );
            }
            catch (GeneralSecurityException e)
            {
                throw new RuntimeException("failed to create BLE pairing-code login proof", e);
            }
        }
        else
        {
            throw new RuntimeException("login needs encrypted connection");
        }
    }

    /**
     * Creates a BLE login write from the persistent sealing password.
     *
     * <p>The password is converted to the sealing key via the normal password-based key derivation
     * and then used to build the encrypted {@code LOGIN} payload for the current key-exchange session.</p>
     *
     * @param cryptoManager active BLE crypto context; encryption must already be enabled
     * @param keyExchangeManager completed BLE key-exchange context for the current session
     * @param password persistent sealing password
     * @throws RuntimeException if encryption is not enabled or the login proof cannot be created
     */
    public Login(CryptoManager cryptoManager, KeyExchangeManager keyExchangeManager, String password)
    {
        if (cryptoManager.encryptionEnabled())
        {
            try
            {
                long generatedKey = KeyGenerator.createKey(password);
                initialize(
                    generatedKey,
                    keyExchangeManager.getClientPublicKey(),
                    keyExchangeManager.getSensorPublicKey(),
                    keyExchangeManager.getSensorChallenge()
                );
            }
            catch (GeneralSecurityException e)
            {
                throw new RuntimeException("failed to create BLE login proof", e);
            }
        }
        else
        {
            throw new RuntimeException("login needs encrypted connection");
        }
    }

    private void initialize(
        long key,
        byte[] clientPublicKey,
        byte[] sensorPublicKey,
        byte[] sensorChallenge
    ) throws GeneralSecurityException
    {
        this.key = key;
        clientNonce = BleLoginCrypto.createClientNonce();
        clientProof = BleLoginCrypto.createClientProof(
            key,
            clientPublicKey,
            sensorPublicKey,
            sensorChallenge,
            clientNonce
        );
        expectedSensorProof = BleLoginCrypto.createSensorProof(
            key,
            clientPublicKey,
            sensorPublicKey,
            sensorChallenge,
            clientNonce
        );
        data = GattByteBuffer.allocate(BleLoginCrypto.CLIENT_NONCE_LEN + BleLoginCrypto.PROOF_LEN)
            .putInt8(clientNonce, 0, clientNonce.length)
            .putInt8(clientProof, 0, clientProof.length)
            .array();
    }

    @Override
    public Characteristic<Login> getCharacteristic()
    {
        return CHARACTERISTIC;
    }

    @Override
    public String toString()
    {
        return "" + getKey();
    }
}
