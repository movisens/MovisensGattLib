package com.movisens.movisensgattlib.security;

import java.security.GeneralSecurityException;

import com.movisens.movisensgattlib.attributes.EnumCommandResult;
import com.movisens.smartgattlib.helper.AbstractAttribute;
import com.movisens.smartgattlib.helper.Characteristic;

/**
 * Minimal GATT read/write seam used by {@link SpakeSession}.
 *
 * <p>It mirrors the two methods of the bluegiga {@code IBleConnection} so a real connection
 * adapts to it trivially, but it lives here (in {@code movisens-gatt-lib}) so {@link SpakeSession}
 * has no dependency on the bluegiga API and so the reusable sensor emulator can implement it
 * without a dependency cycle.</p>
 */
public interface SpakeGattConnection
{
    /** Write a request attribute; returns the sensor command result. */
    EnumCommandResult setAttribute(AbstractAttribute attribute) throws GeneralSecurityException;

    /** Read the response attribute for a characteristic. */
    <T extends AbstractAttribute> T getAttribute(Characteristic<T> characteristic) throws GeneralSecurityException;
}
