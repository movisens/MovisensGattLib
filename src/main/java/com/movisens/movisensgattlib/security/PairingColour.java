package com.movisens.movisensgattlib.security;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The pairing-code colour alphabet and its byte encoding — the single source of truth
 * shared with the firmware. Each colour maps to exactly one byte (the value of the
 * firmware {@code enum class Color}); a pairing code is the sequence of those bytes,
 * one byte per displayed symbol, and that sequence <em>is</em> the SPAKE2 shared secret
 * (see {@link SpakeManager}: {@code w = SHA-256(secret) mod n}).
 *
 * <p>The encoding is a deterministic, public byte mapping — it is part of the
 * {@code SpakeManager} contract (every colour-code consumer needs exactly these bytes),
 * which is why it lives here rather than being re-implemented per consumer. It carries
 * no ability to produce valid confirmations; only the secret bytes.</p>
 *
 * <p>Byte values must stay identical to the firmware
 * {@code embedded-suite/firmware/app/efm32/main/ble/BlePairingCodeBlinker.h Color} enum.
 * {@link #DARK} ({@code 0x0}) is the separator/pause shown between symbols and is never a
 * code symbol. {@link #WHITE}/{@link #YELLOW} are reserved (defined but not currently
 * generated). To add a colour, append it below with its firmware byte value ({@code 0x6}..
 * {@code 0xF}) and — if it should be displayed — add it to {@link #ACTIVE_ALPHABET}; keep
 * both in sync with the firmware.</p>
 */
public enum PairingColour
{
    DARK((byte) 0x0),
    RED((byte) 0x1),
    GREEN((byte) 0x2),
    BLUE((byte) 0x3),
    WHITE((byte) 0x4),
    YELLOW((byte) 0x5);
    // extend here: 0x6..0xF — new colours grow the validation upper bound automatically.

    private final byte value;

    PairingColour(byte value)
    {
        this.value = value;
    }

    /** The firmware {@code Color} byte value. Also the SPAKE2 secret byte for this symbol. */
    public byte value()
    {
        return value;
    }

    /**
     * The active alphabet: the ordered colour list the generator draws from and the device
     * displays. Currently {RED, GREEN, BLUE} (white/yellow/green are hard to tell apart, so
     * the generator uses only these three). Changing the colour selection = change this list
     * (firmware and app in sync); nothing else.
     */
    public static final List<PairingColour> ACTIVE_ALPHABET =
        Collections.unmodifiableList(Arrays.asList(RED, GREEN, BLUE));

    /** {@code true} for every colour that can appear in a code, i.e. everything but {@link #DARK}. */
    public boolean isCodeSymbol()
    {
        return this != DARK;
    }

    /** The defined colour with this byte value, or {@code null} if the value is unknown. */
    public static PairingColour fromValue(byte value)
    {
        for (PairingColour colour : values())
        {
            if (colour.value == value)
            {
                return colour;
            }
        }
        return null;
    }

    /**
     * Table-driven validation: a byte is a valid code symbol iff it is a defined colour and is
     * not {@link #DARK}. The lower bound ({@code 0}/DARK rejected) is the stable invariant; the
     * upper bound follows from the enum and grows automatically with new colours. A side effect
     * of {@code DARK == 0}: a zero-initialised/truncated buffer is detected as invalid rather
     * than read as "all RED".
     */
    public static boolean isValidSymbolValue(byte value)
    {
        PairingColour colour = fromValue(value);
        return colour != null && colour.isCodeSymbol();
    }

    /**
     * The SPAKE2 shared secret for a pairing code: one byte per colour, in order.
     *
     * @throws IllegalArgumentException if any element is {@link #DARK} (not a code symbol)
     */
    public static byte[] toSecret(List<PairingColour> colours)
    {
        byte[] secret = new byte[colours.size()];
        for (int i = 0; i < colours.size(); i++)
        {
            PairingColour colour = colours.get(i);
            if (!colour.isCodeSymbol())
            {
                throw new IllegalArgumentException("DARK is a separator, not a code symbol");
            }
            secret[i] = colour.value;
        }
        return secret;
    }
}
