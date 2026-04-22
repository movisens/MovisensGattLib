package com.movisens.movisensgattlib.security;

import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.ECKey;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.ECParameterSpec;

import javax.crypto.KeyAgreement;

final class Secp256r1Support
{
    static final String CURVE = "secp256r1";

    private static final String[] CURVE_ALIASES = new String[] { CURVE, "prime256v1" };
    private static final Object PARAMS_LOCK = new Object();

    private static volatile ECParameterSpec params;

    private Secp256r1Support()
    {
    }

    static KeyPair generateKeyPair(SecureRandom secureRandom) throws GeneralSecurityException
    {
        GeneralSecurityException lastException = null;

        for (String curveName : CURVE_ALIASES) {
            try {
                KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
                keyPairGenerator.initialize(new ECGenParameterSpec(curveName), secureRandom);
                return keyPairGenerator.generateKeyPair();
            } catch (GeneralSecurityException e) {
                lastException = e;
            }
        }

        throw new GeneralSecurityException("Unable to create secp256r1 key pair", lastException);
    }

    static ECParameterSpec loadParams() throws GeneralSecurityException
    {
        ECParameterSpec current = params;
        if (current != null) {
            return current;
        }

        synchronized (PARAMS_LOCK) {
            current = params;
            if (current != null) {
                return current;
            }

            KeyPair keyPair = generateKeyPair(new SecureRandom());
            current = ((ECKey) keyPair.getPublic()).getParams();
            params = current;
            return current;
        }
    }

    static byte[] calculateSecret(PrivateKey privateKey, byte[] compressedPeerPublicKey) throws GeneralSecurityException
    {
        PublicKey peerPublicKey = Secp256r1PointCodec.decodeCompressed(compressedPeerPublicKey);

        KeyAgreement keyAgreement = KeyAgreement.getInstance("ECDH");
        keyAgreement.init(privateKey);
        keyAgreement.doPhase(peerPublicKey, true);
        return keyAgreement.generateSecret();
    }
}
