package com.movisens.movisensgattlib;

import java.util.UUID;

import com.movisens.movisensgattlib.attributes.AgeFloat;
import com.movisens.movisensgattlib.attributes.SensorLocation;
import com.movisens.movisensgattlib.attributes.BatteryLevelX;
import com.movisens.movisensgattlib.attributes.BatteryLevelBuffered;
import com.movisens.movisensgattlib.attributes.BatteryLevelWaiting;
import com.movisens.movisensgattlib.attributes.Charging;
import com.movisens.movisensgattlib.attributes.ChargingBuffered;
import com.movisens.movisensgattlib.attributes.ChargingWaiting;
import com.movisens.movisensgattlib.attributes.CurrentTime;
import com.movisens.movisensgattlib.attributes.MeasurementStartTime;
import com.movisens.movisensgattlib.attributes.DeleteData;
import com.movisens.movisensgattlib.attributes.SaveEnergy;
import com.movisens.movisensgattlib.attributes.MeasurementEnabled;
import com.movisens.movisensgattlib.attributes.DataAvailable;
import com.movisens.movisensgattlib.attributes.Status;
import com.movisens.movisensgattlib.attributes.TapMarker;
import com.movisens.movisensgattlib.attributes.TapMarkerBuffered;
import com.movisens.movisensgattlib.attributes.TapMarkerWaiting;
import com.movisens.movisensgattlib.attributes.Ecg;
import com.movisens.movisensgattlib.attributes.HrvIsValid;
import com.movisens.movisensgattlib.attributes.HrvIsValidWaiting;
import com.movisens.movisensgattlib.attributes.Rmssd;
import com.movisens.movisensgattlib.attributes.RmssdWaiting;
import com.movisens.movisensgattlib.attributes.HrMean;
import com.movisens.movisensgattlib.attributes.HrMeanWaiting;
import com.movisens.movisensgattlib.attributes.MovementAcceleration;
import com.movisens.movisensgattlib.attributes.MovementAccelerationBuffered;
import com.movisens.movisensgattlib.attributes.MovementAccelerationWaiting;
import com.movisens.movisensgattlib.attributes.Met;
import com.movisens.movisensgattlib.attributes.MetBuffered;
import com.movisens.movisensgattlib.attributes.MetWaiting;
import com.movisens.movisensgattlib.attributes.MetLevel;
import com.movisens.movisensgattlib.attributes.MetLevelBuffered;
import com.movisens.movisensgattlib.attributes.MetLevelWaiting;
import com.movisens.movisensgattlib.attributes.Steps;
import com.movisens.movisensgattlib.attributes.StepsBuffered;
import com.movisens.movisensgattlib.attributes.StepsWaiting;
import com.movisens.movisensgattlib.attributes.Activityclass;
import com.movisens.movisensgattlib.attributes.ActivityclassBuffered;
import com.movisens.movisensgattlib.attributes.ActivityclassWaiting;
import com.movisens.movisensgattlib.attributes.BodyPosition;
import com.movisens.movisensgattlib.attributes.BodyPositionWaiting;
import com.movisens.movisensgattlib.attributes.PimBuffered;
import com.movisens.movisensgattlib.attributes.PimWaiting;
import com.movisens.movisensgattlib.attributes.ZcmBuffered;
import com.movisens.movisensgattlib.attributes.ZcmWaiting;
import com.movisens.movisensgattlib.attributes.InclinationBuffered;
import com.movisens.movisensgattlib.attributes.InclinationWaiting;
import com.movisens.movisensgattlib.attributes.RawaccBuffered;
import com.movisens.movisensgattlib.attributes.RawaccWaiting;
import com.movisens.movisensgattlib.attributes.EdaCounts;
import com.movisens.movisensgattlib.attributes.EdaCountsWaiting;
import com.movisens.movisensgattlib.attributes.EdaSclMean;
import com.movisens.movisensgattlib.attributes.EdaSclMeanWaiting;
import com.movisens.movisensgattlib.attributes.Light;
import com.movisens.movisensgattlib.attributes.LightWaiting;
import com.movisens.movisensgattlib.attributes.ColorTemperature;
import com.movisens.movisensgattlib.attributes.ColorTemperatureWaiting;
import com.movisens.movisensgattlib.attributes.Illumination;
import com.movisens.movisensgattlib.attributes.IlluminationWaiting;
import com.movisens.movisensgattlib.attributes.SensorTemperature;
import com.movisens.movisensgattlib.attributes.SensorTemperatureWaiting;
import com.movisens.movisensgattlib.attributes.AccMagnitude;
import com.movisens.movisensgattlib.attributes.AccMagnitudeWaiting;
import com.movisens.movisensgattlib.attributes.Counter;
import com.movisens.movisensgattlib.attributes.CounterWaiting;
import com.movisens.movisensgattlib.attributes.SkinTemperature;
import com.movisens.movisensgattlib.attributes.SkinTemperatureBuffered;
import com.movisens.movisensgattlib.attributes.SkinTemperature1sBuffered;
import com.movisens.movisensgattlib.attributes.SkinTemperatureWaiting;
import com.movisens.movisensgattlib.attributes.DataTx;
import com.movisens.movisensgattlib.attributes.DataRx;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.UuidObjectMap;

public class MovisensCharacteristics
{

	public static final Characteristic ACC_MAGNITUDE = new Characteristic("e31154aa-3d7c-4725-9fa5-5d95a164b733", "Acc Magnitude", AccMagnitude.class);
	public static final Characteristic ACC_MAGNITUDE_WAITING = new Characteristic("c00c344a-0bf6-48c8-8ed1-090ef63f380a", "Acc Magnitude Waiting", AccMagnitudeWaiting.class);
	public static final Characteristic ACTIVITYCLASS = new Characteristic("2716a61a-ecbe-4bbf-a6b6-9c238acb1382", "Activityclass", Activityclass.class);
	public static final Characteristic ACTIVITYCLASS_BUFFERED = new Characteristic("a496347d-fc9a-426f-94d9-31d663c87105", "Activityclass Buffered", ActivityclassBuffered.class);
	public static final Characteristic ACTIVITYCLASS_WAITING = new Characteristic("bff835f8-0d98-43d1-a98d-723b2c7b6aeb", "Activityclass Waiting", ActivityclassWaiting.class);
	public static final Characteristic AGE_FLOAT = new Characteristic("7562060b-4aff-4422-aec7-77770d2a0530", "Age Float", AgeFloat.class);
	public static final Characteristic BATTERY_LEVEL_BUFFERED = new Characteristic("c7538ae7-b2ec-4905-8ebc-4a0581df4335", "Battery Level Buffered", BatteryLevelBuffered.class);
	public static final Characteristic BATTERY_LEVEL_WAITING = new Characteristic("f84adb7d-a503-44d4-88ba-8583b981b5b2", "Battery Level Waiting", BatteryLevelWaiting.class);
	public static final Characteristic BATTERY_LEVEL_X = new Characteristic("f89edec7-f7e0-94f2-747d-ee7acaa6d413", "Battery Level X", BatteryLevelX.class);
	public static final Characteristic BODY_POSITION = new Characteristic("2abf95be-7496-4e72-b880-f9f00aad553b", "Body Position", BodyPosition.class);
	public static final Characteristic BODY_POSITION_WAITING = new Characteristic("8fbffb12-23ed-498b-b19c-9c9a67f14b75", "Body Position Waiting", BodyPositionWaiting.class);
	public static final Characteristic CHARGING = new Characteristic("d34f2d52-5fcd-491c-b782-6b84e439687e", "Charging", Charging.class);
	public static final Characteristic CHARGING_BUFFERED = new Characteristic("601d030e-b067-4f80-9a36-09aa9fb21670", "Charging Buffered", ChargingBuffered.class);
	public static final Characteristic CHARGING_WAITING = new Characteristic("c1432e2e-aa2e-456b-9c4f-c16ddc449371", "Charging Waiting", ChargingWaiting.class);
	public static final Characteristic COLOR_TEMPERATURE = new Characteristic("cf339284-aa6a-4ef3-b469-a480752576d2", "Color Temperature", ColorTemperature.class);
	public static final Characteristic COLOR_TEMPERATURE_WAITING = new Characteristic("5115262d-6ee7-4021-941d-5eb191d06c9f", "Color Temperature Waiting", ColorTemperatureWaiting.class);
	public static final Characteristic COUNTER = new Characteristic("6840e2b6-a9ea-409c-903e-2c001fcb319f", "Counter", Counter.class);
	public static final Characteristic COUNTER_WAITING = new Characteristic("7bc5b98f-0dab-4e01-befd-b0734b99c756", "Counter Waiting", CounterWaiting.class);
	public static final Characteristic CURRENT_TIME = new Characteristic("f89edec4-d590-764d-530f-8fff5c181606", "Current Time", CurrentTime.class);
	public static final Characteristic DATA_AVAILABLE = new Characteristic("10847e7a-d43f-4b9e-b2f2-3e8546215c3c", "Data Available", DataAvailable.class);
	public static final Characteristic DATA_RX = new Characteristic("e7add780-b042-4876-aae1-112855353cc1", "Data Rx", DataRx.class);
	public static final Characteristic DATA_TX = new Characteristic("9c36476a-1b04-41d7-963e-243a45e22ad9", "Data Tx", DataTx.class);
	public static final Characteristic DELETE_DATA = new Characteristic("f89edec2-9fc2-c29e-ff29-da323b327e44", "Delete Data", DeleteData.class);
	public static final Characteristic ECG = new Characteristic("99b97e05-b8b5-44db-ad20-439f75999a63", "Ecg", Ecg.class);
	public static final Characteristic EDA_COUNTS = new Characteristic("36dcc871-7d30-47ea-aa1c-9693dabdd026", "Eda Counts", EdaCounts.class);
	public static final Characteristic EDA_COUNTS_WAITING = new Characteristic("58699790-e3b6-41ee-9cc9-0584a779b2e0", "Eda Counts Waiting", EdaCountsWaiting.class);
	public static final Characteristic EDA_SCL_MEAN = new Characteristic("a884dc4b-62d6-44ee-bcbf-d0f725d95213", "Eda Scl Mean", EdaSclMean.class);
	public static final Characteristic EDA_SCL_MEAN_WAITING = new Characteristic("47755955-966e-4b75-b79b-ef5c839cb191", "Eda Scl Mean Waiting", EdaSclMeanWaiting.class);
	public static final Characteristic HR_MEAN = new Characteristic("3b999d71-751b-48fa-8817-b7131f47c2da", "Hr Mean", HrMean.class);
	public static final Characteristic HR_MEAN_WAITING = new Characteristic("c806ec67-00be-490a-aa79-1011396f38e8", "Hr Mean Waiting", HrMeanWaiting.class);
	public static final Characteristic HRV_IS_VALID = new Characteristic("5d9724de-501e-475f-b8e6-d0e77ea4d0c1", "Hrv Is Valid", HrvIsValid.class);
	public static final Characteristic HRV_IS_VALID_WAITING = new Characteristic("b2734e22-5c9e-476c-a317-d3fb706df00c", "Hrv Is Valid Waiting", HrvIsValidWaiting.class);
	public static final Characteristic ILLUMINATION = new Characteristic("69803916-a3fc-4748-b951-24ad3bfa6470", "Illumination", Illumination.class);
	public static final Characteristic ILLUMINATION_WAITING = new Characteristic("ae70c353-f228-4915-b36b-9cccd3faca0d", "Illumination Waiting", IlluminationWaiting.class);
	public static final Characteristic INCLINATION_BUFFERED = new Characteristic("f89edebf-9b5b-486d-054f-b3ce3e226d49", "Inclination Buffered", InclinationBuffered.class);
	public static final Characteristic INCLINATION_WAITING = new Characteristic("f89edeb8-dda5-770a-e42d-005ed49f5e29", "Inclination Waiting", InclinationWaiting.class);
	public static final Characteristic LIGHT = new Characteristic("375bf82c-41e8-4ca1-9b95-f8634b1ba2f8", "Light", Light.class);
	public static final Characteristic LIGHT_WAITING = new Characteristic("d166790b-9531-44fd-8314-14f303280de1", "Light Waiting", LightWaiting.class);
	public static final Characteristic MEASUREMENT_ENABLED = new Characteristic("f89edec7-f7e0-94f2-747d-ee7acaa6d412", "Measurement Enabled", MeasurementEnabled.class);
	public static final Characteristic MEASUREMENT_START_TIME = new Characteristic("2d81487d-08f7-47e1-a060-0659d9b4b766", "Measurement Start Time", MeasurementStartTime.class);
	public static final Characteristic MET = new Characteristic("088133e4-bf36-4c10-943a-17e07734d4ba", "Met", Met.class);
	public static final Characteristic MET_BUFFERED = new Characteristic("82e947c3-48a2-4106-8536-b3bdc6b10453", "Met Buffered", MetBuffered.class);
	public static final Characteristic MET_LEVEL = new Characteristic("114dc370-a5d0-4d86-a701-030282a0a271", "Met Level", MetLevel.class);
	public static final Characteristic MET_LEVEL_BUFFERED = new Characteristic("7ba991c9-dfa6-4776-9002-6c9696f90e14", "Met Level Buffered", MetLevelBuffered.class);
	public static final Characteristic MET_LEVEL_WAITING = new Characteristic("547729db-1f9b-422f-a581-ea377ffcadf9", "Met Level Waiting", MetLevelWaiting.class);
	public static final Characteristic MET_WAITING = new Characteristic("e19aa0f5-da3d-4dbf-a4a2-6e8ad6c4d0ce", "Met Waiting", MetWaiting.class);
	public static final Characteristic MOVEMENT_ACCELERATION = new Characteristic("d48d48e3-318f-4a11-8dd2-cb4a9051534f", "Movement Acceleration", MovementAcceleration.class);
	public static final Characteristic MOVEMENT_ACCELERATION_BUFFERED = new Characteristic("9e2da811-041a-43ce-b703-013277f19ae6", "Movement Acceleration Buffered", MovementAccelerationBuffered.class);
	public static final Characteristic MOVEMENT_ACCELERATION_WAITING = new Characteristic("20b6f034-50e5-4fad-92c8-fa20ee4203c6", "Movement Acceleration Waiting", MovementAccelerationWaiting.class);
	public static final Characteristic PIM_BUFFERED = new Characteristic("f89edebc-0267-c40c-be23-515de4ec781e", "Pim Buffered", PimBuffered.class);
	public static final Characteristic PIM_WAITING = new Characteristic("f89edebb-0aab-6b31-506b-a3c76b5af1e4", "Pim Waiting", PimWaiting.class);
	public static final Characteristic RAWACC_BUFFERED = new Characteristic("f89edec1-9fea-e145-f614-8ff69aa7da65", "Rawacc Buffered", RawaccBuffered.class);
	public static final Characteristic RAWACC_WAITING = new Characteristic("f89edec0-b569-ee0d-9589-e4abd1f42692", "Rawacc Waiting", RawaccWaiting.class);
	public static final Characteristic RMSSD = new Characteristic("f89edec1-9fea-e145-f614-8ff69aa7da66", "Rmssd", Rmssd.class);
	public static final Characteristic RMSSD_WAITING = new Characteristic("f89edec0-b569-ee0d-9589-e4abd1f42693", "Rmssd Waiting", RmssdWaiting.class);
	public static final Characteristic SAVE_ENERGY = new Characteristic("f89edebf-9b5b-486d-054f-b3ce3e226d42", "Save Energy", SaveEnergy.class);
	public static final Characteristic SENSOR_LOCATION = new Characteristic("1ffb6b9d-52a7-4de2-a3bb-58ee97facd59", "Sensor Location", SensorLocation.class);
	public static final Characteristic SENSOR_TEMPERATURE = new Characteristic("2c007893-37a4-473d-8c07-09c41324eea5", "Sensor Temperature", SensorTemperature.class);
	public static final Characteristic SENSOR_TEMPERATURE_WAITING = new Characteristic("433a8af8-9839-4057-94aa-ef02fa0af106", "Sensor Temperature Waiting", SensorTemperatureWaiting.class);
	public static final Characteristic SKIN_TEMPERATURE = new Characteristic("f89edec6-a336-5262-448d-400ca97a1c57", "Skin Temperature", SkinTemperature.class);
	public static final Characteristic SKIN_TEMPERATURE_1S_BUFFERED = new Characteristic("99ebde23-1b3e-4084-85c2-18bca6eb5a1a", "Skin Temperature 1s Buffered", SkinTemperature1sBuffered.class);
	public static final Characteristic SKIN_TEMPERATURE_BUFFERED = new Characteristic("78663ddf-83c3-4665-9d04-003c990acf78", "Skin Temperature Buffered", SkinTemperatureBuffered.class);
	public static final Characteristic SKIN_TEMPERATURE_WAITING = new Characteristic("f89edeb7-0d8c-b529-baef-2f9ab82f6cc6", "Skin Temperature Waiting", SkinTemperatureWaiting.class);
	public static final Characteristic STATUS = new Characteristic("f89edec9-b0e0-d44f-45e8-d125177194d5", "Status", Status.class);
	public static final Characteristic STEPS = new Characteristic("8ba3207b-6a87-424d-bde0-4f665f500f04", "Steps", Steps.class);
	public static final Characteristic STEPS_BUFFERED = new Characteristic("58c6374e-9927-414a-b90e-475014af65ba", "Steps Buffered", StepsBuffered.class);
	public static final Characteristic STEPS_WAITING = new Characteristic("9b72b459-d1e5-48fe-9c91-2fb168261b21", "Steps Waiting", StepsWaiting.class);
	public static final Characteristic TAP_MARKER = new Characteristic("207b171c-d7a5-48ef-8e60-6ccb5f0993f4", "Tap Marker", TapMarker.class);
	public static final Characteristic TAP_MARKER_BUFFERED = new Characteristic("5cd3619c-ae4f-42c0-b975-2bca40f2627f", "Tap Marker Buffered", TapMarkerBuffered.class);
	public static final Characteristic TAP_MARKER_WAITING = new Characteristic("eaf03861-68f9-4c78-ae52-1483085031cd", "Tap Marker Waiting", TapMarkerWaiting.class);
	public static final Characteristic ZCM_BUFFERED = new Characteristic("f89edebe-0136-7900-2cec-b4641bb709f5", "Zcm Buffered", ZcmBuffered.class);
	public static final Characteristic ZCM_WAITING = new Characteristic("f89edebd-c3bd-2d57-ba04-7bdeb96e7366", "Zcm Waiting", ZcmWaiting.class);

	private static UuidObjectMap<Characteristic> characteristics = new UuidObjectMap<Characteristic>();

	static
	{
		characteristics.put(ACC_MAGNITUDE);
		characteristics.put(ACC_MAGNITUDE_WAITING);
		characteristics.put(ACTIVITYCLASS);
		characteristics.put(ACTIVITYCLASS_BUFFERED);
		characteristics.put(ACTIVITYCLASS_WAITING);
		characteristics.put(AGE_FLOAT);
		characteristics.put(BATTERY_LEVEL_BUFFERED);
		characteristics.put(BATTERY_LEVEL_WAITING);
		characteristics.put(BATTERY_LEVEL_X);
		characteristics.put(BODY_POSITION);
		characteristics.put(BODY_POSITION_WAITING);
		characteristics.put(CHARGING);
		characteristics.put(CHARGING_BUFFERED);
		characteristics.put(CHARGING_WAITING);
		characteristics.put(COLOR_TEMPERATURE);
		characteristics.put(COLOR_TEMPERATURE_WAITING);
		characteristics.put(COUNTER);
		characteristics.put(COUNTER_WAITING);
		characteristics.put(CURRENT_TIME);
		characteristics.put(DATA_AVAILABLE);
		characteristics.put(DATA_RX);
		characteristics.put(DATA_TX);
		characteristics.put(DELETE_DATA);
		characteristics.put(ECG);
		characteristics.put(EDA_COUNTS);
		characteristics.put(EDA_COUNTS_WAITING);
		characteristics.put(EDA_SCL_MEAN);
		characteristics.put(EDA_SCL_MEAN_WAITING);
		characteristics.put(HR_MEAN);
		characteristics.put(HR_MEAN_WAITING);
		characteristics.put(HRV_IS_VALID);
		characteristics.put(HRV_IS_VALID_WAITING);
		characteristics.put(ILLUMINATION);
		characteristics.put(ILLUMINATION_WAITING);
		characteristics.put(INCLINATION_BUFFERED);
		characteristics.put(INCLINATION_WAITING);
		characteristics.put(LIGHT);
		characteristics.put(LIGHT_WAITING);
		characteristics.put(MEASUREMENT_ENABLED);
		characteristics.put(MEASUREMENT_START_TIME);
		characteristics.put(MET);
		characteristics.put(MET_BUFFERED);
		characteristics.put(MET_LEVEL);
		characteristics.put(MET_LEVEL_BUFFERED);
		characteristics.put(MET_LEVEL_WAITING);
		characteristics.put(MET_WAITING);
		characteristics.put(MOVEMENT_ACCELERATION);
		characteristics.put(MOVEMENT_ACCELERATION_BUFFERED);
		characteristics.put(MOVEMENT_ACCELERATION_WAITING);
		characteristics.put(PIM_BUFFERED);
		characteristics.put(PIM_WAITING);
		characteristics.put(RAWACC_BUFFERED);
		characteristics.put(RAWACC_WAITING);
		characteristics.put(RMSSD);
		characteristics.put(RMSSD_WAITING);
		characteristics.put(SAVE_ENERGY);
		characteristics.put(SENSOR_LOCATION);
		characteristics.put(SENSOR_TEMPERATURE);
		characteristics.put(SENSOR_TEMPERATURE_WAITING);
		characteristics.put(SKIN_TEMPERATURE);
		characteristics.put(SKIN_TEMPERATURE_1S_BUFFERED);
		characteristics.put(SKIN_TEMPERATURE_BUFFERED);
		characteristics.put(SKIN_TEMPERATURE_WAITING);
		characteristics.put(STATUS);
		characteristics.put(STEPS);
		characteristics.put(STEPS_BUFFERED);
		characteristics.put(STEPS_WAITING);
		characteristics.put(TAP_MARKER);
		characteristics.put(TAP_MARKER_BUFFERED);
		characteristics.put(TAP_MARKER_WAITING);
		characteristics.put(ZCM_BUFFERED);
		characteristics.put(ZCM_WAITING);
	}

	/**
	 * Looks up the characteristic for the given UUID. If the UUID is not found, a lookup in the smartgattlib is performed.
	 * 
	 * @param uuid the UUID to look for
	 * @return the corresponding characteristic or the DEFAULT characteristic of smartgattlib if UUID is not found
	 */
	public static Characteristic lookup(UUID uuid)
	{
		Characteristic result = characteristics.get(uuid);
		if (result == null)
		{
			result = com.movisens.smartgattlib.Characteristics.lookup(uuid);
		}
		return result;
	}

}
