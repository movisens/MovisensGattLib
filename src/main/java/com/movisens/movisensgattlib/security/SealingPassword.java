package com.movisens.movisensgattlib.security;

import java.security.GeneralSecurityException;

import com.movisens.smartgattlib.helper.GattByteBuffer;
import com.movisens.smartgattlib.security.KeyGenerator;

/**
 * Converts the user-visible sealing password into the PAKE secret bytes for sealed BLE access.
 *
 * <p>This mirrors the app-side {@code SealSensor} conversion. The sensor stores only the
 * resulting 64-bit key; it does not derive the key from the password. On sealed sensors the
 * firmware uses the stored key bytes as the SPAKE2 secret.</p>
 */
public final class SealingPassword
{
    private SealingPassword()
    {
    }

    public static byte[] toSecret(String password)
    {
        try
        {
            long key = KeyGenerator.createKey(password);
            return GattByteBuffer.allocate(8).putInt64(key).array();
        }
        catch (GeneralSecurityException e)
        {
            throw new IllegalStateException("failed to derive sealing key", e);
        }
    }
}
