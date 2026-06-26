package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractWriteAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.PlainTextAttribute;

/**
 * Second part of the client SPAKE2 share pA (SEC1 compressed, bytes 20..32).
 * This triggers a command_result notification.
 * Possible results:
 * - ok: the fragment was accepted.
 * - INVALID_PAKE_STATE: no active PAKE session, failed PAKE state or invalid length.
 * If pake_client_share_1 was already written and this write completes the share, the
 * final result can also be:
 * - INVALID_POINT: the assembled client share is not a valid point.
 * - UNEXPECTED_EXCEPTION: asynchronous PAKE share computation failed.
 */
public class PakeClientShare2 extends AbstractWriteAttribute implements PlainTextAttribute
{
    public static final Characteristic<PakeClientShare2> CHARACTERISTIC = MovisensCharacteristics.PAKE_CLIENT_SHARE_2;

    public PakeClientShare2(byte[] data)
    {
        this.data = data;
    }

    @Override
    public Characteristic<PakeClientShare2> getCharacteristic()
    {
        return CHARACTERISTIC;
    }
}
