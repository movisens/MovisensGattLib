package com.movisens.movisensgattlib.security;

import java.security.GeneralSecurityException;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.movisensgattlib.attributes.PakeStart;
import com.movisens.movisensgattlib.attributes.EnumCommandResult;
import com.movisens.smartgattlib.helper.AbstractAttribute;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;

/**
 * Drives the balanced-SPAKE2 handshake over a {@link SpakeGattConnection} and returns the
 * negotiated AES session key. This is the one place that performs the explicit PAKE start plus
 * the two write/read round trips described by {@link SpakeManager}; the threaded tests and the
 * GUI used to duplicate this sequence.
 *
 * <p>The handshake's key confirmation <em>is</em> the authentication — there is no separate
 * login step. The session key is only returned after the sensor confirmation has verified
 * (MITM-checked). A wrong secret or an active rate-limit lockout surfaces as a
 * {@link PakeException} carrying the sensor's {@link EnumCommandResult}; the caller maps the
 * code to a user message (e.g. a {@code PAKE_RATE_LIMITED_*} wait time) and must not start an
 * automatic PAKE retry.</p>
 */
public final class SpakeSession
{
    private SpakeSession()
    {
    }

    /** Starts a fresh sensor-side PAKE session (blink colour code on unsealed sensors, arm sealed login). */
    public static void start(SpakeGattConnection connection) throws GeneralSecurityException
    {
        requireOk(connection.setAttribute(new PakeStart(Boolean.TRUE)));
    }

    /**
     * Starts a fresh PAKE session on the sensor, runs the full handshake and returns the 16-byte
     * AES session key.
     *
     * @param connection   the GATT read/write seam to drive
     * @param sensorId     SPAKE2 party A identity (sensor serial bytes from the advertised name)
     * @param clientId     SPAKE2 party B identity (see {@link SpakeIdentities#clientId()})
     * @param sharedSecret the secret bytes (onboarding colour code or sealing-password key)
     * @throws PakeException if the sensor rejects the confirmation (wrong secret) or a lockout is
     *                       active; {@link PakeException#getResult()} carries the sensor result code
     * @throws GeneralSecurityException on any other crypto/handshake error
     */
    public static byte[] run(SpakeGattConnection connection, byte[] sensorId, byte[] clientId, byte[] sharedSecret)
        throws GeneralSecurityException
    {
        start(connection);
        return runExistingSession(connection, sensorId, clientId, sharedSecret);
    }

    /**
     * Continues a PAKE session that was already started explicitly via {@link #start(SpakeGattConnection)}.
     * This is used by onboarding UIs that must first trigger the sensor to blink the colour code,
     * wait for the user to read it, and only then send the SPAKE shares.
     */
    public static byte[] runExistingSession(
        SpakeGattConnection connection, byte[] sensorId, byte[] clientId, byte[] sharedSecret
    ) throws GeneralSecurityException
    {
        SpakeManager manager = new SpakeManager(sensorId, clientId, sharedSecret);

        // Round 1: write the client share, read the sensor share.
        for (AbstractAttribute request : manager.getShareRequestAttributes())
        {
            requireOk(connection.setAttribute(request));
        }
        manager.setSensorShareResponse(new AbstractReadAttribute[] {
            connection.getAttribute(MovisensCharacteristics.PAKE_SENSOR_SHARE_1),
            connection.getAttribute(MovisensCharacteristics.PAKE_SENSOR_SHARE_2)
        });

        // Round 2: write the client confirm. The sensor verifies it here, so a wrong secret or an
        // active lockout surfaces as the command result of this write — there is no sensor confirm
        // to read in that case.
        for (AbstractAttribute request : manager.getConfirmRequestAttributes())
        {
            requireOk(connection.setAttribute(request));
        }

        // The sensor accepted our confirm; read and verify its confirm (MITM check), then the key.
        return manager.getAesKey(new AbstractReadAttribute[] {
            connection.getAttribute(MovisensCharacteristics.PAKE_SENSOR_CONFIRM_1),
            connection.getAttribute(MovisensCharacteristics.PAKE_SENSOR_CONFIRM_2)
        });
    }

    private static void requireOk(EnumCommandResult result) throws PakeException
    {
        if (result != EnumCommandResult.OK)
        {
            throw new PakeException(result.getName(), result);
        }
    }
}
