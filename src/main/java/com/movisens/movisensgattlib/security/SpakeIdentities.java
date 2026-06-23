package com.movisens.movisensgattlib.security;

import java.nio.charset.StandardCharsets;

/**
 * Shared SPAKE2 identity conventions (see {@link SpakeManager}).
 *
 * <p>The {@code clientId} (RFC 9382 party B, {@code idB}) is the constant ASCII string
 * {@code "client"} for every client; it provides role/domain separation only and must
 * match the firmware byte-for-byte. The {@code sensorId} (party A, {@code idA}) is not a
 * constant — it is the sensor serial taken from the BLE advertised name — so it is not
 * defined here.</p>
 */
public final class SpakeIdentities
{
    /** The constant {@code clientId} string. */
    public static final String CLIENT_ID = "client";

    /** A fresh copy of the {@code clientId} bytes (US-ASCII). */
    public static byte[] clientId()
    {
        return CLIENT_ID.getBytes(StandardCharsets.US_ASCII);
    }

    private SpakeIdentities()
    {
    }
}
