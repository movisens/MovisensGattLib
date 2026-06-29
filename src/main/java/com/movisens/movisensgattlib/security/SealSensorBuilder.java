package com.movisens.movisensgattlib.security;

import java.security.GeneralSecurityException;
import java.util.Objects;

import com.movisens.movisensgattlib.attributes.SealSensor;
import com.movisens.smartgattlib.security.CryptoManager;
import com.movisens.smartgattlib.security.KeyGenerator;

public final class SealSensorBuilder
{
    private SealSensorBuilder()
    {
    }

    /**
     * @param cryptoManager active BLE crypto context; encryption must already be enabled
     * @param password      the sealing password
     */
    public static SealSensor create(CryptoManager cryptoManager, String password)
    {
        Objects.requireNonNull(cryptoManager, "cryptoManager");
        if (!cryptoManager.encryptionEnabled())
        {
            throw new IllegalStateException("sealing needs encrypted connection");
        }

        try
        {
            return new SealSensor(KeyGenerator.createKey(password));
        }
        catch (GeneralSecurityException e)
        {
            throw new IllegalStateException("failed to derive sealing key", e);
        }
    }
}
