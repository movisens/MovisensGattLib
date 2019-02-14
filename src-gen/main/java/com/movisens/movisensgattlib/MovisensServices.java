package com.movisens.movisensgattlib;

import java.util.UUID;

import com.movisens.smartgattlib.helper.Service;
import com.movisens.smartgattlib.helper.UuidObjectMap;

public class MovisensServices
{

	public static final Service AMBIENT = new Service("d0f0f790-66c9-4e1f-bf48-1628a7ad89f9", "Ambient");
	public static final Service EDA = new Service("eb08670d-f244-4511-b43e-04bba968b693", "Eda");
	public static final Service HRV = new Service("0bd51666-e7cb-469b-8e4d-2742f1ba77cd", "Hrv");
	public static final Service MARKER = new Service("32062bba-7843-4ad6-94ea-95c66909edcf", "Marker");
	public static final Service MOVISENS_BATTERY = new Service("27b66685-62e5-4e76-8c02-d625600ed2c6", "Movisens Battery");
	public static final Service MOVISENS_USER_DATA = new Service("17e28d0f-5f44-421f-97d5-667655e24460", "Movisens User Data");
	public static final Service PHYSICAL_ACTIVITY = new Service("0302c2b2-ce64-4542-b819-666d20d415bd", "Physical Activity");
	public static final Service RESPIRATION = new Service("da87d1a7-749c-4711-bd54-c625043ecd83", "Respiration");
	public static final Service SENSOR_CONTROL = new Service("f89edeb6-e4e8-928b-4cfa-ebc07fce1768", "Sensor Control");
	public static final Service SKIN_TEMPERATURE = new Service("247af432-444c-4211-8b9d-2c8512cfdf4a", "Skin Temperature");

	private static UuidObjectMap<Service> services = new UuidObjectMap<Service>();

	static
	{
		services.put(AMBIENT);
		services.put(EDA);
		services.put(HRV);
		services.put(MARKER);
		services.put(MOVISENS_BATTERY);
		services.put(MOVISENS_USER_DATA);
		services.put(PHYSICAL_ACTIVITY);
		services.put(RESPIRATION);
		services.put(SENSOR_CONTROL);
		services.put(SKIN_TEMPERATURE);
	}

	/**
	 * Looks up the service for the given UUID. If the UUID is not found, a lookup in the smartgattlib is performed.
	 * 
	 * @param uuid the UUID to look for
	 * @return the corresponding service or the DEFAULT service of smartgattlib if UUID is not found
	 */
	public static Service lookup(UUID uuid)
	{
		Service result = services.get(uuid);
		if (result == null)
		{
			result = com.movisens.smartgattlib.Services.lookup(uuid);
		}
		return result;
	}
}
