package com.movisens.movisensgattlib.attributes;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractWriteAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;
import com.movisens.smartgattlib.security.CryptoManager;
import com.movisens.smartgattlib.security.KeyGenerator;

public class Login extends AbstractWriteAttribute
{

    public static final Characteristic<Login> CHARACTERISTIC = MovisensCharacteristics.LOGIN;

    private long[] key;

    public long[] getKey()
    {
        return key;
    }

    public Login(CryptoManager cryptoManager, String password)
    {
        if (cryptoManager.encryptionEnabled())
        {
            key = KeyGenerator.createKey(password);

            GattByteBuffer bb = GattByteBuffer.allocate(16);
            bb.putInt64(key[0]);

            data = bb.array();
        }
        else
        {
            throw new RuntimeException("login needs encrypted connection");
        }
    }

    @Override
    public Characteristic<Login> getCharacteristic()
    {
        return CHARACTERISTIC;
    }

    @Override
    public String toString()
    {
        return "" + getKey()[0];
    }
}
