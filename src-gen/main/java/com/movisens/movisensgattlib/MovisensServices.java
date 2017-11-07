package com.movisens.movisensgattlib;

import java.util.UUID;

import com.movisens.smartgattlib.helper.Service;
import com.movisens.smartgattlib.helper.UuidObjectMap;

public class MovisensServices
{

	public static final Service AMBIENT_SERVICE = new Service("d0f0f790-66c9-4e1f-bf48-1628a7ad89f9", "Ambient Service");
	public static final Service BATTERY_SERVICE_MOVISENS = new Service("27b66685-62e5-4e76-8c02-d625600ed2c6", "Battery Service Movisens");
	public static final Service CABLEREPLACEMENT_SERVICE = new Service("0bd51666-e7cb-469b-8e4d-2742f1ba77cc", "Cablereplacement Service");
	public static final Service ECG_SERVICE = new Service("7d1ad222-c7f5-4352-a3ed-783dda0a23ed", "Ecg Service");
	public static final Service EDA_SERVICE = new Service("10847e7a-d43f-4b9e-b2f2-3e8546215c3c", "Eda Service");
	public static final Service EMV_TEST_SERVICE = new Service("9724d49b-dc55-492c-9b4d-d6d24c3e6c7c", "Emv Test Service");
	public static final Service HRV_SERVICE = new Service("0bd51666-e7cb-469b-8e4d-2742f1ba77cd", "Hrv Service");
	public static final Service MARKER_SERVICE = new Service("32062bba-7843-4ad6-94ea-95c66909edcf", "Marker Service");
	public static final Service PHYSICAL_ACTIVITY_SERVICE = new Service("0302c2b2-ce64-4542-b819-666d20d415bd", "Physical Activity Service");
	public static final Service SENSOR_CONTROL_SERVICE = new Service("f89edeb6-e4e8-928b-4cfa-ebc07fce1768", "Sensor Control Service");
	public static final Service SKIN_TEMPERATURE_SERVICE = new Service("247af432-444c-4211-8b9d-2c8512cfdf4a", "Skin Temperature Service");
	public static final Service USER_DATA_MOVISENS = new Service("17e28d0f-5f44-421f-97d5-667655e24460", "User Data Movisens");

	private static UuidObjectMap<Service> services = new UuidObjectMap<Service>();

	static
	{
		services.put(AMBIENT_SERVICE);
		services.put(BATTERY_SERVICE_MOVISENS);
		services.put(CABLEREPLACEMENT_SERVICE);
		services.put(ECG_SERVICE);
		services.put(EDA_SERVICE);
		services.put(EMV_TEST_SERVICE);
		services.put(HRV_SERVICE);
		services.put(MARKER_SERVICE);
		services.put(PHYSICAL_ACTIVITY_SERVICE);
		services.put(SENSOR_CONTROL_SERVICE);
		services.put(SKIN_TEMPERATURE_SERVICE);
		services.put(USER_DATA_MOVISENS);
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
