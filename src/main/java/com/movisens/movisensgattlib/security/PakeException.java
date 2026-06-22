package com.movisens.movisensgattlib.security;

import java.security.GeneralSecurityException;

/** Signals a failed or out-of-state balanced-SPAKE2 handshake step. */
public class PakeException extends GeneralSecurityException
{
    private static final long serialVersionUID = 1L;

    public PakeException(String message)
    {
        super(message);
    }
}
