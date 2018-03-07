package com.movisens.movisensgattlib.helper;

import java.util.Date;

public interface BufferedAttribute<T extends AbstractData> {

	Date getTime();
	double getSamplerate();
	
	String[] getValueNames();
	String[] getValueUnits();
	double[][] getValues();
	
	Iterable<T> getData();
}
