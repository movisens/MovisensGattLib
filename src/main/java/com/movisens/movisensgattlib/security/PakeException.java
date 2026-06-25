package com.movisens.movisensgattlib.security;

import java.security.GeneralSecurityException;

import com.movisens.movisensgattlib.attributes.EnumCommandResult;

/**
 * Signals a failed or out-of-state balanced-SPAKE2 handshake step.
 *
 * <p>When the failure was reported by the sensor as a command result (e.g.
 * {@link EnumCommandResult#WRONG_CODE} for a rejected client confirm, or one of the
 * {@code PAKE_RATE_LIMITED_*} codes for an active lockout), that result is carried in
 * {@link #getResult()} so callers can branch on it (show the matching wait time, suppress
 * automatic retries) without parsing the message.</p>
 */
public class PakeException extends GeneralSecurityException
{
    private static final long serialVersionUID = 1L;

    private final EnumCommandResult result;

    public PakeException(String message)
    {
        this(message, null);
    }

    public PakeException(String message, EnumCommandResult result)
    {
        super(message);
        this.result = result;
    }

    /** The sensor command result that caused this failure, or {@code null} if not applicable. */
    public EnumCommandResult getResult()
    {
        return result;
    }
}
