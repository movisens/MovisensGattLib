package com.movisens.movisensgattlib.security;

import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Arrays;

import com.movisens.smartgattlib.helper.AbstractAttribute;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;

/**
 * Balanced-SPAKE2 counterpart of {@link KeyExchangeManager}: a flat,
 * attribute-array facade over {@link SpakePairingClient} for callers that drive a
 * GATT session the {@code KeyExchangeManager} way — the request attributes to write
 * are returned by the corresponding methods, and the responses are handed back in as
 * the {@link AbstractReadAttribute} instances that were read.
 *
 * <p>The handshake is secret-agnostic, so the same manager covers both PAKE use cases
 * from the sealing design: the {@code sharedSecret} is the onboarding colour code on an
 * unsealed sensor, or the stored sealing password for sealed access. Either way it is
 * just {@code byte[]}; the byte encoding must match the firmware.</p>
 *
 * <p>Unlike the single round trip of the old ECDH key exchange, SPAKE2 needs two
 * write/read round trips, so the request/response pair is doubled:</p>
 *
 * <pre>{@code
 * SpakeManager manager = new SpakeManager(sensorId, clientId, sharedSecret);
 *
 * // Round 1: client share -> sensor share
 * for (AbstractAttribute request : manager.getShareRequestAttributes()) {
 *     bleConnection.setAttribute(request);
 * }
 * manager.setSensorShareResponse(new AbstractReadAttribute[] {
 *     bleConnection.getAttribute(MovisensCharacteristics.PAKE_SENSOR_SHARE_1),
 *     bleConnection.getAttribute(MovisensCharacteristics.PAKE_SENSOR_SHARE_2)
 * });
 *
 * // Round 2: client confirm -> sensor confirm
 * for (AbstractAttribute request : manager.getConfirmRequestAttributes()) {
 *     bleConnection.setAttribute(request);
 * }
 * byte[] aesKey = manager.getAesKey(new AbstractReadAttribute[] {
 *     bleConnection.getAttribute(MovisensCharacteristics.PAKE_SENSOR_CONFIRM_1),
 *     bleConnection.getAttribute(MovisensCharacteristics.PAKE_SENSOR_CONFIRM_2)
 * });
 * cryptoManager.setKey(aesKey); // only reached if the sensor confirmation verified (MITM-checked)
 * }</pre>
 *
 * <p>{@link #getAesKey(AbstractReadAttribute[])} throws {@link PakeException} if the
 * sensor confirmation does not match — the key is never released for a wrong secret
 * (colour code / sealing password) or a man-in-the-middle.</p>
 */
public class SpakeManager
{
    private final SpakePairingClient client;

    /**
     * @param sensorId    the sensor's SPAKE2 identity (RFC 9382 party A, {@code idA}). Public,
     *                    not a secret. It is folded into the handshake transcript and binds the
     *                    session to one specific sensor. The agreed value is the sensor's
     *                    <strong>serial number</strong> bytes, derived by the app from the BLE
     *                    advertised name. This is safe: the firmware binds its own true serial,
     *                    so a relay/spoof to a different sensor cannot confirm (it fails rather
     *                    than redirecting). The extracted bytes must equal the firmware's
     *                    {@code idA} serial byte-for-byte (same substring, same encoding); mind
     *                    advertisement truncation so the full serial is recovered consistently.
     * @param clientId    the client's SPAKE2 identity (RFC 9382 party B, {@code idB}). Public,
     *                    not a secret. The agreed value is the constant ASCII string
     *                    {@code "client"} for every client; it provides role/domain separation
     *                    only and must match the firmware byte-for-byte.
     * @param sharedSecret the SPAKE2 password: the onboarding colour code (unsealed sensor)
     *                    or the sealing password (sealed access), as agreed with the firmware.
     */
    public SpakeManager(byte[] sensorId, byte[] clientId, byte[] sharedSecret)
        throws GeneralSecurityException
    {
        this.client = new SpakePairingClient(sensorId, clientId, sharedSecret);
    }

    SpakeManager(byte[] sensorId, byte[] clientId, byte[] sharedSecret, SecureRandom rng)
        throws GeneralSecurityException
    {
        this.client = new SpakePairingClient(sensorId, clientId, sharedSecret, rng);
    }

    /** Round 1 request: the two {@code pake_client_share} attributes to write. */
    public AbstractAttribute[] getShareRequestAttributes() throws GeneralSecurityException
    {
        return new AbstractAttribute[] { client.clientShare1(), client.clientShare2() };
    }

    /** Consumes the two {@code pake_sensor_share} attributes that were read back. */
    public void setSensorShareResponse(AbstractReadAttribute[] response) throws GeneralSecurityException
    {
        client.setSensorShare(reassemble(response));
    }

    /** Round 2 request: the two {@code pake_client_confirm} attributes to write. */
    public AbstractAttribute[] getConfirmRequestAttributes() throws GeneralSecurityException
    {
        return new AbstractAttribute[] { client.clientConfirm1(), client.clientConfirm2() };
    }

    /**
     * Consumes the two {@code pake_sensor_confirm} attributes that were read back,
     * verifies them and returns the negotiated 16-byte AES session key.
     *
     * @throws PakeException if the sensor confirmation is invalid (wrong secret / MITM)
     */
    public byte[] getAesKey(AbstractReadAttribute[] response) throws GeneralSecurityException
    {
        client.verifySensorConfirm(reassemble(response));
        return client.sessionKey();
    }

    private static byte[] reassemble(AbstractReadAttribute[] response)
    {
        if (response == null || response.length < 2)
        {
            throw new IllegalArgumentException("expected the two response attribute parts");
        }
        byte[] part1 = response[0].getRawData();
        byte[] part2 = response[1].getRawData();
        byte[] out = Arrays.copyOf(part1, part1.length + part2.length);
        System.arraycopy(part2, 0, out, part1.length, part2.length);
        return out;
    }
}
