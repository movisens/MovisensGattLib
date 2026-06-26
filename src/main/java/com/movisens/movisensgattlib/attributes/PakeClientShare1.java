package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractWriteAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.PlainTextAttribute;

/**
 * First part of the client SPAKE2 share pA = x*G + w*M (SEC1 compressed, bytes 0..19).
 * Requires an active PAKE session started via pake_start.
 * This triggers a command_result notification.
 * Possible results:
 * - ok: the fragment was accepted.
 * - INVALID_PAKE_STATE: no active PAKE session, failed PAKE state or invalid length.
 * If pake_client_share_2 was already written and this write completes the share, the
 * final result can also be:
 * - INVALID_POINT: the assembled client share is not a valid point.
 * - UNEXPECTED_EXCEPTION: asynchronous PAKE share computation failed.
 */
public class PakeClientShare1 extends AbstractWriteAttribute implements PlainTextAttribute
{
    public static final Characteristic<PakeClientShare1> CHARACTERISTIC = MovisensCharacteristics.PAKE_CLIENT_SHARE_1;

    public PakeClientShare1(byte[] data)
    {
        this.data = data;
    }

    @Override
    public Characteristic<PakeClientShare1> getCharacteristic()
    {
        return CHARACTERISTIC;
    }
}
