package com.movisens.movisensgattlib.attributes;

import java.security.NoSuchAlgorithmException;

import com.movisens.movisensdevgattlib.security.KeyGenerator;
import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractWriteAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class Login extends AbstractWriteAttribute
{

	public static final Characteristic<Login> CHARACTERISTIC = MovisensCharacteristics.LOGIN;
	
	private long[] key;
	
	public long[] getKey()
	{
		return key;
	}
	
	public Login(String password) throws NoSuchAlgorithmException
	{
		this.key = KeyGenerator.createKey(password);
		GattByteBuffer bb = GattByteBuffer.allocate(16);
		bb.putInt64(key[0]);
		this.data = bb.array();
	}

	@Override
	public Characteristic<Login> getCharacteristic()
	{
		return CHARACTERISTIC;
	}

	@Override
	public String toString()
	{
		return ""+getKey()[0];
	}
}
