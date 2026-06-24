package com.movisens.movisensgattlib.security;

import java.security.GeneralSecurityException;
import java.security.SecureRandom;

import com.movisens.movisensgattlib.attributes.EnumCommandResult;
import com.movisens.movisensgattlib.security.MockedSpakeSensor.Attr;

/**
 * Reusable emulation of the sensor side of the balanced-SPAKE2 handshake (RFC 9382 party A),
 * built on the RFC-verified {@link MockedSpakeSensor}/{@code Spake2Role}. It is meant to behave
 * exactly like the future firmware so that switching consumers from this emulator to a real
 * sensor is a no-op:
 *
 * <ul>
 *   <li>holds the "blinked" secret (colour code or sealing-password key) as {@code byte[]};</li>
 *   <li>runs share/confirm and gates the session key on a correct client confirm
 *       (otherwise {@link EnumCommandResult#KEY_CONFIRMATION_FAILED});</li>
 *   <li>models the persistent PAKE failure counter and the 60-min / 2-h / 4-h / 8-h / 24-h
 *       lockout tiers, returning the matching {@code PAKE_RATE_LIMITED_*} code while locked.</li>
 * </ul>
 *
 * <p>This is the crypto/state core; {@link MockSpakeBleConnection} maps the GATT attributes onto
 * it. Rate-limit timing uses an injected {@link SensorClock} so tests stay deterministic.</p>
 */
public final class SpakeSensorEmulator
{
    /** Lockout durations per tier (failures 1-3, 4-6, 7-9, 10-12, 13+). */
    private static final long[] TIER_DURATIONS_MS = {
        60L * 60_000,        // 60 min
        2L * 60L * 60_000,   // 2 h
        4L * 60L * 60_000,   // 4 h
        8L * 60L * 60_000,   // 8 h
        24L * 60L * 60_000   // 24 h (cap)
    };

    private static final EnumCommandResult[] TIER_CODES = {
        EnumCommandResult.PAKE_RATE_LIMITED_60_MIN,
        EnumCommandResult.PAKE_RATE_LIMITED_2_H,
        EnumCommandResult.PAKE_RATE_LIMITED_4_H,
        EnumCommandResult.PAKE_RATE_LIMITED_8_H,
        EnumCommandResult.PAKE_RATE_LIMITED_24_H
    };

    private final byte[] secret;
    private final byte[] sensorId;
    private final byte[] clientId;
    private final SecureRandom rng;
    private final SensorClock clock;

    private boolean sealed;

    // Persistent across sessions/reconnects: the failure counter and the active lockout deadline.
    private int failureCount;
    private long lockedUntilMillis;

    // Per-PAKE-session state.
    private MockedSpakeSensor session;
    private boolean clientConfirmed;
    private boolean sessionStarted;

    public SpakeSensorEmulator(byte[] secret, byte[] sensorId, byte[] clientId, boolean sealed,
                               SecureRandom rng, SensorClock clock)
    {
        this.secret = secret.clone();
        this.sensorId = sensorId.clone();
        this.clientId = clientId.clone();
        this.sealed = sealed;
        this.rng = rng;
        this.clock = clock;
    }

    public boolean isSealed()
    {
        return sealed;
    }

    /** Models a successful {@code SealSensor} write after an authenticated session. */
    public void seal()
    {
        this.sealed = true;
    }

    /** Models a successful {@code UnsealSensor} write after an authenticated session. */
    public void unseal()
    {
        this.sealed = false;
    }

    /**
     * Explicitly starts a new PAKE session. Returns OK, or the active {@code PAKE_RATE_LIMITED_*}
     * code if a lockout is in effect.
     */
    public EnumCommandResult startPairing()
    {
        if (isLockedOut())
        {
            return activeLockoutCode();
        }
        session = null;
        clientConfirmed = false;
        sessionStarted = true;
        return EnumCommandResult.OK;
    }

    /**
     * Begins the active PAKE session from the client share. Returns OK, or the active
     * {@code PAKE_RATE_LIMITED_*} code if a lockout is in effect.
     */
    public EnumCommandResult onClientShare(byte[] clientShare) throws GeneralSecurityException
    {
        if (isLockedOut())
        {
            return activeLockoutCode();
        }
        if (!sessionStarted)
        {
            return EnumCommandResult.INVALID_PAKE_STATE;
        }
        // Fresh session: a new role each time so a previous (failed) attempt cannot be resumed.
        session = new MockedSpakeSensor(secret, sensorId, clientId, rng);
        clientConfirmed = false;
        session.write(Attr.CLIENT_SHARE, clientShare);
        return EnumCommandResult.OK;
    }

    public byte[] sensorShare() throws GeneralSecurityException
    {
        requireSession();
        return session.read(Attr.SENSOR_SHARE);
    }

    /**
     * Verifies the client confirm. A wrong confirm is reported as
     * {@link EnumCommandResult#KEY_CONFIRMATION_FAILED} and registers a failure (which raises the
     * counter and arms the next lockout tier); a correct confirm returns OK and resets the counter.
     * If a lockout is active the matching {@code PAKE_RATE_LIMITED_*} code is returned instead.
     */
    public EnumCommandResult onClientConfirm(byte[] clientConfirm) throws GeneralSecurityException
    {
        if (isLockedOut())
        {
            return activeLockoutCode();
        }
        requireSession();
        try
        {
            session.write(Attr.CLIENT_CONFIRM, clientConfirm);
        }
        catch (PakeException rejected)
        {
            registerFailure();
            session = null;
            return EnumCommandResult.KEY_CONFIRMATION_FAILED;
        }
        clientConfirmed = true;
        failureCount = 0;
        lockedUntilMillis = 0;
        return EnumCommandResult.OK;
    }

    public byte[] sensorConfirm() throws GeneralSecurityException
    {
        if (!clientConfirmed)
        {
            throw new PakeException("INVALID_PAKE_STATE: client not confirmed");
        }
        return session.read(Attr.SENSOR_CONFIRM);
    }

    public byte[] sessionKey()
    {
        return session.sessionKey();
    }

    /** {@code true} once the current session's client confirm has verified. */
    public boolean isSessionAuthenticated()
    {
        return clientConfirmed;
    }

    // --- rate limiting -------------------------------------------------------------------------

    private boolean isLockedOut()
    {
        return clock.nowMillis() < lockedUntilMillis;
    }

    private EnumCommandResult activeLockoutCode()
    {
        return TIER_CODES[tierIndex(failureCount)];
    }

    private void registerFailure()
    {
        failureCount++;
        lockedUntilMillis = clock.nowMillis() + TIER_DURATIONS_MS[tierIndex(failureCount)];
    }

    /** Maps the failure count to a tier index: failures 1-3 -> 0, 4-6 -> 1, ..., 13+ -> 4. */
    private static int tierIndex(int failures)
    {
        if (failures <= 0)
        {
            return 0;
        }
        return Math.min((failures - 1) / 3, TIER_DURATIONS_MS.length - 1);
    }

    private void requireSession() throws PakeException
    {
        if (session == null)
        {
            throw new PakeException("INVALID_PAKE_STATE: no active PAKE session");
        }
    }
}
