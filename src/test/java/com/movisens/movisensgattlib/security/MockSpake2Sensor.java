package com.movisens.movisensgattlib.security;

import java.security.GeneralSecurityException;
import java.security.SecureRandom;

/**
 * In-memory sensor mock for the balanced-SPAKE2 colour-code pairing. The only
 * interface to it is the GATT attribute set: the client writes/reads byte payloads
 * via {@link #write}/{@link #read}, exactly as it would over BLE.
 *
 * <p>The mock holds the "blinked" colour code and runs the sensor side of the
 * handshake with {@link Spake2Role}.</p>
 */
final class MockSpake2Sensor
{
    /** GATT characteristics that form the wire interface (map to {@code pake_*}). */
    enum Attr
    {
        CLIENT_SHARE,   // write
        SENSOR_SHARE,   // read
        CLIENT_CONFIRM, // write
        SENSOR_CONFIRM  // read
    }

    private final Spake2Role role;
    private byte[] sensorShare;
    private boolean clientConfirmed;

    MockSpake2Sensor(byte[] colourCode, byte[] idA, byte[] idB, SecureRandom rng) throws GeneralSecurityException
    {
        // The sensor is party A (uses M); the app/client is party B.
        this.role = Spake2Role.forColourCode(Spake2Role.Role.A, idA, idB, colourCode, rng);
    }

    void write(Attr attribute, byte[] payload) throws GeneralSecurityException
    {
        switch (attribute)
        {
            case CLIENT_SHARE:
                // sensor picks its ephemeral, makes its share (compressed wire format)
                sensorShare = P256.compress(role.createShare());
                role.setPeerShare(P256.decompress(payload)); // derive K + keys from the client share
                break;
            case CLIENT_CONFIRM:
                if (!role.verifyPeerConfirm(payload))
                {
                    throw new PakeException("KEY_CONFIRMATION_FAILED");
                }
                clientConfirmed = true;
                break;
            default:
                throw new PakeException("INVALID_PAKE_STATE: cannot write " + attribute);
        }
    }

    byte[] read(Attr attribute) throws GeneralSecurityException
    {
        switch (attribute)
        {
            case SENSOR_SHARE:
                if (sensorShare == null)
                {
                    throw new PakeException("INVALID_PAKE_STATE: no client share yet");
                }
                return sensorShare.clone();
            case SENSOR_CONFIRM:
                if (!clientConfirmed)
                {
                    throw new PakeException("INVALID_PAKE_STATE: client not confirmed");
                }
                return role.ownConfirm();
            default:
                throw new PakeException("INVALID_PAKE_STATE: cannot read " + attribute);
        }
    }

    byte[] sessionKey()
    {
        return role.sessionKey();
    }
}
