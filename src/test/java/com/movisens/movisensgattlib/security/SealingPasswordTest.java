package com.movisens.movisensgattlib.security;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.junit.Test;

import com.movisens.smartgattlib.helper.GattByteBuffer;
import com.movisens.smartgattlib.security.KeyGenerator;

public class SealingPasswordTest
{
    @Test
    public void toSecretUsesDerivedSealingKeyBytes() throws Exception
    {
        long key = KeyGenerator.createKey("Tr0ub4dor&3");

        assertArrayEquals(GattByteBuffer.allocate(8).putInt64(key).array(),
            SealingPassword.toSecret("Tr0ub4dor&3"));
    }

    @Test
    public void toSecretDoesNotUsePasswordAsciiBytes()
    {
        assertFalse(Arrays.equals(
            "Tr0ub4dor&3".getBytes(StandardCharsets.US_ASCII),
            SealingPassword.toSecret("Tr0ub4dor&3")));
    }
}
