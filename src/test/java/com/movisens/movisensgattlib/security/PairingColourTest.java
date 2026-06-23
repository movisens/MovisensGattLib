package com.movisens.movisensgattlib.security;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;

import org.junit.Test;

public class PairingColourTest
{
    @Test
    public void colourValuesMatchTheFirmwareEnum()
    {
        assertEquals(0x0, PairingColour.DARK.value());
        assertEquals(0x1, PairingColour.RED.value());
        assertEquals(0x2, PairingColour.GREEN.value());
        assertEquals(0x3, PairingColour.BLUE.value());
        assertEquals(0x4, PairingColour.WHITE.value());
        assertEquals(0x5, PairingColour.YELLOW.value());
    }

    @Test
    public void activeAlphabetIsRedGreenBlueInOrder()
    {
        assertEquals(Arrays.asList(PairingColour.RED, PairingColour.GREEN, PairingColour.BLUE),
            PairingColour.ACTIVE_ALPHABET);
    }

    @Test
    public void toSecretIsOneBytePerColourInOrder()
    {
        byte[] secret = PairingColour.toSecret(Arrays.asList(
            PairingColour.RED, PairingColour.GREEN, PairingColour.BLUE,
            PairingColour.RED, PairingColour.GREEN, PairingColour.BLUE));
        assertArrayEquals(new byte[] {1, 2, 3, 1, 2, 3}, secret);
    }

    @Test
    public void toSecretRejectsDark()
    {
        try
        {
            PairingColour.toSecret(Arrays.asList(PairingColour.RED, PairingColour.DARK));
            fail("DARK is a separator, not a code symbol");
        }
        catch (IllegalArgumentException expected)
        {
        }
    }

    @Test
    public void validationRejectsDarkAndUnknownButAcceptsDefinedColours()
    {
        assertFalse("DARK (0) must be invalid", PairingColour.isValidSymbolValue((byte) 0x0));
        assertTrue(PairingColour.isValidSymbolValue((byte) 0x1));
        assertTrue(PairingColour.isValidSymbolValue((byte) 0x3));
        // reserved colours are defined, hence valid symbols even if not currently generated
        assertTrue(PairingColour.isValidSymbolValue((byte) 0x5));
        assertFalse("undefined value must be invalid", PairingColour.isValidSymbolValue((byte) 0x6));
    }

    @Test
    public void fromValueReturnsNullForUnknown()
    {
        assertEquals(PairingColour.BLUE, PairingColour.fromValue((byte) 0x3));
        assertNull(PairingColour.fromValue((byte) 0x6));
    }
}
