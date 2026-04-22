package com.movisens.movisensgattlib.attributes;

import java.security.GeneralSecurityException;
import java.util.Arrays;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.movisensgattlib.security.BleMitmProofs;
import com.movisens.movisensgattlib.security.KeyExchangeManager;
import com.movisens.smartgattlib.helper.AbstractWriteAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;
import com.movisens.smartgattlib.security.CryptoManager;
import com.movisens.smartgattlib.security.KeyGenerator;

public class Login extends AbstractWriteAttribute
{

    public static final Characteristic<Login> CHARACTERISTIC = MovisensCharacteristics.LOGIN;

    private long[] key;
    private byte[] clientNonce;
    private byte[] clientProof;
    private byte[] expectedSensorProof;

    public long[] getKey()
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

    public Login(CryptoManager cryptoManager, KeyExchangeManager keyExchangeManager, String password)
    {
        if (cryptoManager.encryptionEnabled())
        {
            try
            {
                key = KeyGenerator.createKey(password);
                clientNonce = BleMitmProofs.createClientNonce();
                clientProof = BleMitmProofs.createClientProof(
                    key[0],
                    keyExchangeManager.getClientPublicKey(),
                    keyExchangeManager.getSensorPublicKey(),
                    keyExchangeManager.getSensorChallenge(),
                    clientNonce
                );
                expectedSensorProof = BleMitmProofs.createSensorProof(
                    key[0],
                    keyExchangeManager.getClientPublicKey(),
                    keyExchangeManager.getSensorPublicKey(),
                    keyExchangeManager.getSensorChallenge(),
                    clientNonce
                );

                GattByteBuffer bb = GattByteBuffer.allocate(BleMitmProofs.CLIENT_NONCE_LEN + BleMitmProofs.PROOF_LEN);
                bb.putInt8(clientNonce, 0, clientNonce.length);
                bb.putInt8(clientProof, 0, clientProof.length);
                data = bb.array();
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

    @Override
    public Characteristic<Login> getCharacteristic()
    {
        return CHARACTERISTIC;
    }

    @Override
    public String toString()
    {
        return "" + getKey()[0];
    }
}
