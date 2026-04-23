package com.movisens.movisensgattlib.security;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BleLoginCryptoTest
{
    @Test
    public void createPairingCodeKeyEncodesBase5Digits()
    {
        assertEquals(970L, BleLoginCrypto.createPairingCodeKey(new int[] {0, 1, 2, 3, 4, 0}));
    }

    @Test
    public void createPairingCodeKeyAcceptsMaximumDigits()
    {
        assertEquals(15624L, BleLoginCrypto.createPairingCodeKey(new int[] {4, 4, 4, 4, 4, 4}));
    }

    @Test(expected = IllegalArgumentException.class)
    public void createPairingCodeKeyRejectsWrongLength()
    {
        BleLoginCrypto.createPairingCodeKey(new int[] {0, 1, 2, 3, 4});
    }

    @Test(expected = IllegalArgumentException.class)
    public void createPairingCodeKeyRejectsDigitsOutsideRange()
    {
        BleLoginCrypto.createPairingCodeKey(new int[] {0, 1, 2, 3, 4, 5});
    }
}
