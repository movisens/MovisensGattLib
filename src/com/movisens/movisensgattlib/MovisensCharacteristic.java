package com.movisens.movisensgattlib;

import java.util.HashMap;
import java.util.UUID;

import com.movisens.smartgattlib.GattUtils;

public class MovisensCharacteristic {
	public static final UUID BATTERY_LEVEL_X = UUID.fromString("f89edec7-f7e0-94f2-747d-ee7acaa6d413");
	public static final UUID CURRENT_TIME = UUID.fromString("f89edec4-d590-764d-530f-8fff5c181606");
	public static final UUID CMD_CLEAR_ALL_DATA = UUID.fromString("f89edec2-9fc2-c29e-ff29-da323b327e44");
	public static final UUID STATUS = UUID.fromString("f89edec9-b0e0-d44f-45e8-d125177194d5");
	public static final UUID AUTHORIZE = UUID.fromString("f89edebf-9b5b-486d-054f-b3ce3e226d42");
	public static final UUID MEASUREMENT_ENABLED = UUID.fromString("f89edec7-f7e0-94f2-747d-ee7acaa6d412");
	public static final UUID DATA_AVAILABLE = UUID.fromString("10847e7a-d43f-4b9e-b2f2-3e8546215c3c");
	public static final UUID TEMPERATURE = UUID.fromString("f89edec6-a336-5262-448d-400ca97a1c57");
	public static final UUID TEMPERATURE_WAITING = UUID.fromString("f89edeb7-0d8c-b529-baef-2f9ab82f6cc6");
	public static final UUID PIM = UUID.fromString("f89edebc-0267-c40c-be23-515de4ec781e");
	public static final UUID PIM_WAITING = UUID.fromString("f89edebb-0aab-6b31-506b-a3c76b5af1e4");
	public static final UUID ZCM = UUID.fromString("f89edebe-0136-7900-2cec-b4641bb709f5");
	public static final UUID ZCM_WAITING = UUID.fromString("f89edebd-c3bd-2d57-ba04-7bdeb96e7366");
	public static final UUID INCLINATION = UUID.fromString("f89edebf-9b5b-486d-054f-b3ce3e226d49");
	public static final UUID INCLINATION_WAITING = UUID.fromString("f89edeb8-dda5-770a-e42d-005ed49f5e29");
	public static final UUID RAWACC = UUID.fromString("f89edec1-9fea-e145-f614-8ff69aa7da65");
	public static final UUID RAWACC_WAITING = UUID.fromString("f89edec0-b569-ee0d-9589-e4abd1f42692");
	public static final UUID HRVISVALID = UUID.fromString("5d9724de-501e-475f-b8e6-d0e77ea4d0c1");
	public static final UUID HRVISVALID_WAITING = UUID.fromString("b2734e22-5c9e-476c-a317-d3fb706df00c");
	public static final UUID RMSSD = UUID.fromString("f89edec1-9fea-e145-f614-8ff69aa7da66");
	public static final UUID RMSSD_WAITING = UUID.fromString("f89edec0-b569-ee0d-9589-e4abd1f42693");
	public static final UUID MOVEMENTACCELERATION = UUID.fromString("d48d48e3-318f-4a11-8dd2-cb4a9051534f");
	public static final UUID MOVEMENTACCELERATION_WAITING = UUID.fromString("20b6f034-50e5-4fad-92c8-fa20ee4203c6");
	public static final UUID MET = UUID.fromString("088133e4-bf36-4c10-943a-17e07734d4ba");
	public static final UUID MET_WAITING = UUID.fromString("e19aa0f5-da3d-4dbf-a4a2-6e8ad6c4d0ce");
	public static final UUID MET_LEVEL = UUID.fromString("114dc370-a5d0-4d86-a701-030282a0a271");
	public static final UUID MET_LEVEL_WAITING = UUID.fromString("547729db-1f9b-422f-a581-ea377ffcadf9");
	public static final UUID STEP_COUNT = UUID.fromString("8ba3207b-6a87-424d-bde0-4f665f500f04");
	public static final UUID STEP_COUNT_WAITING = UUID.fromString("9b72b459-d1e5-48fe-9c91-2fb168261b21");
	public static final UUID ACTIVITYCLASS = UUID.fromString("2716a61a-ecbe-4bbf-a6b6-9c238acb1382");
	public static final UUID ACTIVITYCLASS_WAITING = UUID.fromString("bff835f8-0d98-43d1-a98d-723b2c7b6aeb");
	public static final UUID AGE = new UUID((0x2A80L << 32) | 0x1000, GattUtils.leastSigBits);
	public static final UUID AGE_FLOAT = UUID.fromString("7562060b-4aff-4422-aec7-77770d2a0530");
	public static final UUID GENDER = new UUID((0x2A8CL << 32) | 0x1000, GattUtils.leastSigBits);
	public static final UUID WEIGHT = new UUID((0x2A98L << 32) | 0x1000, GattUtils.leastSigBits);
	public static final UUID HEIGHT = new UUID((0x2A8EL << 32) | 0x1000, GattUtils.leastSigBits);
	public static final UUID SENSOR_LOCATION = UUID.fromString("1ffb6b9d-52a7-4de2-a3bb-58ee97facd59");
	private static HashMap<UUID, String> attributes = new HashMap<UUID, String>();

	static {
		attributes.put(BATTERY_LEVEL_X, "Battery level X");
		attributes.put(CURRENT_TIME, "Current time");
		attributes.put(CMD_CLEAR_ALL_DATA, "Clear all data");
		attributes.put(STATUS, "Status");
		attributes.put(AUTHORIZE, "Authorize");
		attributes.put(MEASUREMENT_ENABLED, "Measurement enabled");
		attributes.put(DATA_AVAILABLE, "Data available");
		attributes.put(TEMPERATURE, "Temperature");
		attributes.put(TEMPERATURE_WAITING, "Temperature waiting");
		attributes.put(PIM, "PIM");
		attributes.put(PIM_WAITING, "PIM waiting");
		attributes.put(ZCM, "ZCM");
		attributes.put(ZCM_WAITING, "ZCM waiting");
		attributes.put(RAWACC, "RAW Acceleration");
		attributes.put(RAWACC_WAITING, "RAW Acceleration waiting");
		attributes.put(HRVISVALID, "HRV is valid");
		attributes.put(HRVISVALID_WAITING, "HRV is valid waiting");
		attributes.put(RMSSD, "RMSSD");
		attributes.put(RMSSD_WAITING, "RMSSD waiting");
		attributes.put(MOVEMENTACCELERATION, "Movement acceleration");
		attributes.put(MOVEMENTACCELERATION_WAITING, "Movement acceleration waiting");
		attributes.put(MET, "MET");
		attributes.put(MET_WAITING, "MET waiting");
		attributes.put(MET_LEVEL, "MET Level");
		attributes.put(MET_LEVEL_WAITING, "MET Level waiting");
		attributes.put(STEP_COUNT, "Steps");
		attributes.put(STEP_COUNT_WAITING, "Steps waiting");
		attributes.put(ACTIVITYCLASS, "Activity class");
		attributes.put(ACTIVITYCLASS_WAITING, "Activity class waiting");
		attributes.put(AGE, "Age of Participant");
		attributes.put(AGE_FLOAT, "Age in float value of Participant");
		attributes.put(GENDER, "Gender of participant");
		attributes.put(WEIGHT, "Weight of participant");
		attributes.put(HEIGHT, "Height of participant");
		attributes.put(SENSOR_LOCATION, "Sensor location at participant");
	}

	public static String lookup(UUID uuid, String defaultName) {
		String name = attributes.get(uuid);
		return name == null ? defaultName : name;
	}
}