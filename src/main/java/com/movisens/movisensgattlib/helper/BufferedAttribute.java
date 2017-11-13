package com.movisens.movisensgattlib.helper;

import java.util.Date;

public interface BufferedAttribute {

	Date getTime();
	double getSamplerate();
	
	String[] getValueNames();
	String[] getValueUnits();
	double[][] getValues();
}
