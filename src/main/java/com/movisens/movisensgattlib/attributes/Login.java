package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractWriteAttribute;
import com.movisens.smartgattlib.helper.Characteristic;

/**
 * Inert placeholder for the legacy {@code LOGIN} characteristic.
 *
 * <p>The old ECDH key-exchange + HMAC login protocol ({@code KeyExchangeManager} + {@code Login}
 * proof + {@code AUTH_CONFIRM}) has been replaced by the balanced-SPAKE2 handshake
 * ({@link com.movisens.movisensgattlib.security.SpakeManager}), whose key confirmation <em>is</em>
 * the authentication. The login crypto has been removed and no app code uses this characteristic.</p>
 *
 * <p>This thin class only remains because the shared GATT definition still declares the
 * {@code LOGIN} characteristic for the not-yet-migrated firmware (so the generated
 * {@code MovisensCharacteristics} references {@code Login.class}). It carries no behaviour.</p>
 */
public class Login extends AbstractWriteAttribute
{
    public static final Characteristic<Login> CHARACTERISTIC = MovisensCharacteristics.LOGIN;

    public Login(byte[] data)
    {
        this.data = data;
    }

    @Override
    public Characteristic<Login> getCharacteristic()
    {
        return CHARACTERISTIC;
    }
}
