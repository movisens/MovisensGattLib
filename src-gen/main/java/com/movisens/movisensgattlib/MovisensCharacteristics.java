package com.movisens.movisensgattlib;

import java.util.UUID;

import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.helper.UuidObjectMap;
import com.movisens.smartgattlib.helper.AbstractAttribute;
import com.movisens.movisensgattlib.attributes.*;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;

public class MovisensCharacteristics
{

	public static final Characteristic<AccMagnitude> ACC_MAGNITUDE = new Characteristic<AccMagnitude>("e31154aa-3d7c-4725-9fa5-5d95a164b733", "Acc Magnitude", AccMagnitude.class);
	public static final Characteristic<AgeFloat> AGE_FLOAT = new Characteristic<AgeFloat>("7562060b-4aff-4422-aec7-77770d2a0530", "Age Float", AgeFloat.class);
	public static final BufferedCharacteristic<BatteryLevelBuffered, BatteryLevelData> BATTERY_LEVEL_BUFFERED = new BufferedCharacteristic<BatteryLevelBuffered, BatteryLevelData>("c7538ae7-b2ec-4905-8ebc-4a0581df4335", "Battery Level Buffered", BatteryLevelBuffered.class);
	public static final Characteristic<BatteryLevelWaiting> BATTERY_LEVEL_WAITING = new Characteristic<BatteryLevelWaiting>("f84adb7d-a503-44d4-88ba-8583b981b5b2", "Battery Level Waiting", BatteryLevelWaiting.class);
	public static final Characteristic<BodyPosition> BODY_POSITION = new Characteristic<BodyPosition>("2abf95be-7496-4e72-b880-f9f00aad553b", "Body Position", BodyPosition.class);
	public static final BufferedCharacteristic<BodyPositionBuffered, BodyPositionData> BODY_POSITION_BUFFERED = new BufferedCharacteristic<BodyPositionBuffered, BodyPositionData>("fda6f11e-a1d0-41da-b611-5ab3ec34f6ca", "Body Position Buffered", BodyPositionBuffered.class);
	public static final Characteristic<BodyPositionWaiting> BODY_POSITION_WAITING = new Characteristic<BodyPositionWaiting>("8fbffb12-23ed-498b-b19c-9c9a67f14b75", "Body Position Waiting", BodyPositionWaiting.class);
	public static final Characteristic<Charging> CHARGING = new Characteristic<Charging>("d34f2d52-5fcd-491c-b782-6b84e439687e", "Charging", Charging.class);
	public static final BufferedCharacteristic<ChargingBuffered, ChargingData> CHARGING_BUFFERED = new BufferedCharacteristic<ChargingBuffered, ChargingData>("601d030e-b067-4f80-9a36-09aa9fb21670", "Charging Buffered", ChargingBuffered.class);
	public static final Characteristic<ChargingWaiting> CHARGING_WAITING = new Characteristic<ChargingWaiting>("c1432e2e-aa2e-456b-9c4f-c16ddc449371", "Charging Waiting", ChargingWaiting.class);
	public static final Characteristic<Counter> COUNTER = new Characteristic<Counter>("6840e2b6-a9ea-409c-903e-2c001fcb319f", "Counter", Counter.class);
	public static final Characteristic<CounterWaiting> COUNTER_WAITING = new Characteristic<CounterWaiting>("7bc5b98f-0dab-4e01-befd-b0734b99c756", "Counter Waiting", CounterWaiting.class);
	public static final Characteristic<CurrentDrain> CURRENT_DRAIN = new Characteristic<CurrentDrain>("690e309f-0248-469c-b049-ee29b8230842", "Current Drain", CurrentDrain.class);
	public static final BufferedCharacteristic<CurrentDrainBuffered, CurrentDrainData> CURRENT_DRAIN_BUFFERED = new BufferedCharacteristic<CurrentDrainBuffered, CurrentDrainData>("80a36368-e30f-4b4e-a2ec-c5ba68958648", "Current Drain Buffered", CurrentDrainBuffered.class);
	public static final Characteristic<CurrentDrainWaiting> CURRENT_DRAIN_WAITING = new Characteristic<CurrentDrainWaiting>("732fbd29-2603-4807-b5d5-acdb773648c0", "Current Drain Waiting", CurrentDrainWaiting.class);
	public static final Characteristic<CurrentTime> CURRENT_TIME = new Characteristic<CurrentTime>("f89edec4-d590-764d-530f-8fff5c181606", "Current Time", CurrentTime.class);
	public static final Characteristic<CustomData> CUSTOM_DATA = new Characteristic<CustomData>("0086b101-7f7d-4249-bfae-1999065a68c2", "Custom Data", CustomData.class);
	public static final Characteristic<DataAvailable> DATA_AVAILABLE = new Characteristic<DataAvailable>("10847e7a-d43f-4b9e-b2f2-3e8546215c3c", "Data Available", DataAvailable.class);
	public static final Characteristic<DataRx> DATA_RX = new Characteristic<DataRx>("e7add780-b042-4876-aae1-112855353cc1", "Data Rx", DataRx.class);
	public static final Characteristic<DataTx> DATA_TX = new Characteristic<DataTx>("9c36476a-1b04-41d7-963e-243a45e22ad9", "Data Tx", DataTx.class);
	public static final Characteristic<DeleteData> DELETE_DATA = new Characteristic<DeleteData>("f89edec2-9fc2-c29e-ff29-da323b327e44", "Delete Data", DeleteData.class);
	public static final Characteristic<Ecg> ECG = new Characteristic<Ecg>("99b97e05-b8b5-44db-ad20-439f75999a63", "Ecg", Ecg.class);
	public static final Characteristic<EdaSclMean> EDA_SCL_MEAN = new Characteristic<EdaSclMean>("a884dc4b-62d6-44ee-bcbf-d0f725d95213", "Eda Scl Mean", EdaSclMean.class);
	public static final BufferedCharacteristic<EdaSclMeanBuffered, EdaSclMeanData> EDA_SCL_MEAN_BUFFERED = new BufferedCharacteristic<EdaSclMeanBuffered, EdaSclMeanData>("663af1bc-2fa0-43c0-b452-2b8c1efb7f9d", "Eda Scl Mean Buffered", EdaSclMeanBuffered.class);
	public static final Characteristic<EdaSclMeanWaiting> EDA_SCL_MEAN_WAITING = new Characteristic<EdaSclMeanWaiting>("47755955-966e-4b75-b79b-ef5c839cb191", "Eda Scl Mean Waiting", EdaSclMeanWaiting.class);
	public static final Characteristic<HrMean> HR_MEAN = new Characteristic<HrMean>("3b999d71-751b-48fa-8817-b7131f47c2da", "Hr Mean", HrMean.class);
	public static final BufferedCharacteristic<HrMeanBuffered, HrMeanData> HR_MEAN_BUFFERED = new BufferedCharacteristic<HrMeanBuffered, HrMeanData>("1d9533d1-8c6e-4b6a-b242-d0713be204f0", "Hr Mean Buffered", HrMeanBuffered.class);
	public static final Characteristic<HrMeanWaiting> HR_MEAN_WAITING = new Characteristic<HrMeanWaiting>("c806ec67-00be-490a-aa79-1011396f38e8", "Hr Mean Waiting", HrMeanWaiting.class);
	public static final Characteristic<HrvIsValid> HRV_IS_VALID = new Characteristic<HrvIsValid>("5d9724de-501e-475f-b8e6-d0e77ea4d0c1", "Hrv Is Valid", HrvIsValid.class);
	public static final BufferedCharacteristic<HrvIsValidBuffered, HrvIsValidData> HRV_IS_VALID_BUFFERED = new BufferedCharacteristic<HrvIsValidBuffered, HrvIsValidData>("0524f2f1-d8da-4ef6-9e3b-43d6ed0ec518", "Hrv Is Valid Buffered", HrvIsValidBuffered.class);
	public static final Characteristic<HrvIsValidWaiting> HRV_IS_VALID_WAITING = new Characteristic<HrvIsValidWaiting>("b2734e22-5c9e-476c-a317-d3fb706df00c", "Hrv Is Valid Waiting", HrvIsValidWaiting.class);
	public static final Characteristic<Inclination> INCLINATION = new Characteristic<Inclination>("e165b5d0-d83f-4a5c-86a6-306ca1ddf0ef", "Inclination", Inclination.class);
	public static final BufferedCharacteristic<InclinationBuffered, InclinationData> INCLINATION_BUFFERED = new BufferedCharacteristic<InclinationBuffered, InclinationData>("f89edebf-9b5b-486d-054f-b3ce3e226d49", "Inclination Buffered", InclinationBuffered.class);
	public static final Characteristic<InclinationWaiting> INCLINATION_WAITING = new Characteristic<InclinationWaiting>("f89edeb8-dda5-770a-e42d-005ed49f5e29", "Inclination Waiting", InclinationWaiting.class);
	public static final Characteristic<Light> LIGHT = new Characteristic<Light>("375bf82c-41e8-4ca1-9b95-f8634b1ba2f8", "Light", Light.class);
	public static final BufferedCharacteristic<LightBuffered, LightData> LIGHT_BUFFERED = new BufferedCharacteristic<LightBuffered, LightData>("7e5dd77b-67b7-42dd-be7a-822373391b2f", "Light Buffered", LightBuffered.class);
	public static final Characteristic<LightRgb> LIGHT_RGB = new Characteristic<LightRgb>("db32d0ca-fda0-4298-9d2f-1b109eb95a2f", "Light RGB", LightRgb.class);
	public static final BufferedCharacteristic<LightRgbBuffered, LightRgbData> LIGHT_RGB_BUFFERED = new BufferedCharacteristic<LightRgbBuffered, LightRgbData>("2c4abbf8-8da6-4e47-afcd-18034d67c5ee", "Light RGB buffered", LightRgbBuffered.class);
	public static final Characteristic<LightRgbWaiting> LIGHT_RGB_WAITING = new Characteristic<LightRgbWaiting>("c758f5a6-516d-4125-b8de-ae3ebcabeabc", "Light RGB waiting", LightRgbWaiting.class);
	public static final Characteristic<LightWaiting> LIGHT_WAITING = new Characteristic<LightWaiting>("d166790b-9531-44fd-8314-14f303280de1", "Light Waiting", LightWaiting.class);
	public static final Characteristic<MeasurementEnabled> MEASUREMENT_ENABLED = new Characteristic<MeasurementEnabled>("f89edec7-f7e0-94f2-747d-ee7acaa6d412", "Measurement Enabled", MeasurementEnabled.class);
	public static final Characteristic<MeasurementStartTime> MEASUREMENT_START_TIME = new Characteristic<MeasurementStartTime>("2d81487d-08f7-47e1-a060-0659d9b4b766", "Measurement Start Time", MeasurementStartTime.class);
	public static final Characteristic<Met> MET = new Characteristic<Met>("088133e4-bf36-4c10-943a-17e07734d4ba", "Met", Met.class);
	public static final BufferedCharacteristic<MetBuffered, MetData> MET_BUFFERED = new BufferedCharacteristic<MetBuffered, MetData>("82e947c3-48a2-4106-8536-b3bdc6b10453", "Met Buffered", MetBuffered.class);
	public static final Characteristic<MetLevel> MET_LEVEL = new Characteristic<MetLevel>("114dc370-a5d0-4d86-a701-030282a0a271", "Met Level", MetLevel.class);
	public static final BufferedCharacteristic<MetLevelBuffered, MetLevelData> MET_LEVEL_BUFFERED = new BufferedCharacteristic<MetLevelBuffered, MetLevelData>("7ba991c9-dfa6-4776-9002-6c9696f90e14", "Met Level Buffered", MetLevelBuffered.class);
	public static final Characteristic<MetLevelWaiting> MET_LEVEL_WAITING = new Characteristic<MetLevelWaiting>("547729db-1f9b-422f-a581-ea377ffcadf9", "Met Level Waiting", MetLevelWaiting.class);
	public static final Characteristic<MetWaiting> MET_WAITING = new Characteristic<MetWaiting>("e19aa0f5-da3d-4dbf-a4a2-6e8ad6c4d0ce", "Met Waiting", MetWaiting.class);
	public static final Characteristic<MovementAcceleration> MOVEMENT_ACCELERATION = new Characteristic<MovementAcceleration>("d48d48e3-318f-4a11-8dd2-cb4a9051534f", "Movement Acceleration", MovementAcceleration.class);
	public static final BufferedCharacteristic<MovementAccelerationBuffered, MovementAccelerationData> MOVEMENT_ACCELERATION_BUFFERED = new BufferedCharacteristic<MovementAccelerationBuffered, MovementAccelerationData>("9e2da811-041a-43ce-b703-013277f19ae6", "Movement Acceleration Buffered", MovementAccelerationBuffered.class);
	public static final Characteristic<MovementAccelerationWaiting> MOVEMENT_ACCELERATION_WAITING = new Characteristic<MovementAccelerationWaiting>("20b6f034-50e5-4fad-92c8-fa20ee4203c6", "Movement Acceleration Waiting", MovementAccelerationWaiting.class);
	public static final BufferedCharacteristic<PimBuffered, PimData> PIM_BUFFERED = new BufferedCharacteristic<PimBuffered, PimData>("f89edebc-0267-c40c-be23-515de4ec781e", "Pim Buffered", PimBuffered.class);
	public static final Characteristic<PimWaiting> PIM_WAITING = new Characteristic<PimWaiting>("f89edebb-0aab-6b31-506b-a3c76b5af1e4", "Pim Waiting", PimWaiting.class);
	public static final BufferedCharacteristic<RawaccBuffered, RawaccData> RAWACC_BUFFERED = new BufferedCharacteristic<RawaccBuffered, RawaccData>("f89edec1-9fea-e145-f614-8ff69aa7da65", "Rawacc Buffered", RawaccBuffered.class);
	public static final Characteristic<RawaccWaiting> RAWACC_WAITING = new Characteristic<RawaccWaiting>("f89edec0-b569-ee0d-9589-e4abd1f42692", "Rawacc Waiting", RawaccWaiting.class);
	public static final Characteristic<Rmssd> RMSSD = new Characteristic<Rmssd>("f89edec1-9fea-e145-f614-8ff69aa7da66", "Rmssd", Rmssd.class);
	public static final BufferedCharacteristic<RmssdBuffered, RmssdData> RMSSD_BUFFERED = new BufferedCharacteristic<RmssdBuffered, RmssdData>("1bc36d57-595b-499e-8f2a-fa2275bcabc3", "Rmssd Buffered", RmssdBuffered.class);
	public static final Characteristic<RmssdWaiting> RMSSD_WAITING = new Characteristic<RmssdWaiting>("f89edec0-b569-ee0d-9589-e4abd1f42693", "Rmssd Waiting", RmssdWaiting.class);
	public static final Characteristic<SaveEnergy> SAVE_ENERGY = new Characteristic<SaveEnergy>("f89edebf-9b5b-486d-054f-b3ce3e226d42", "Save Energy", SaveEnergy.class);
	public static final Characteristic<SendBufferedData> SEND_BUFFERED_DATA = new Characteristic<SendBufferedData>("8b7446a0-372a-4841-aa5e-3b97d30a45b3", "Send Buffered Data", SendBufferedData.class);
	public static final Characteristic<SensorLocation> SENSOR_LOCATION = new Characteristic<SensorLocation>("1ffb6b9d-52a7-4de2-a3bb-58ee97facd59", "Sensor Location", SensorLocation.class);
	public static final Characteristic<SensorTemperature> SENSOR_TEMPERATURE = new Characteristic<SensorTemperature>("2c007893-37a4-473d-8c07-09c41324eea5", "Sensor Temperature", SensorTemperature.class);
	public static final BufferedCharacteristic<SensorTemperatureBuffered, SensorTemperatureData> SENSOR_TEMPERATURE_BUFFERED = new BufferedCharacteristic<SensorTemperatureBuffered, SensorTemperatureData>("869c06de-f52a-4a90-9a3a-ca5fd35d6707", "Sensor Temperature Buffered", SensorTemperatureBuffered.class);
	public static final Characteristic<SensorTemperatureWaiting> SENSOR_TEMPERATURE_WAITING = new Characteristic<SensorTemperatureWaiting>("433a8af8-9839-4057-94aa-ef02fa0af106", "Sensor Temperature Waiting", SensorTemperatureWaiting.class);
	public static final Characteristic<SkinTemperature> SKIN_TEMPERATURE = new Characteristic<SkinTemperature>("f89edec6-a336-5262-448d-400ca97a1c57", "Skin Temperature", SkinTemperature.class);
	public static final BufferedCharacteristic<SkinTemperature1sBuffered, SkinTemperature1sData> SKIN_TEMPERATURE_1S_BUFFERED = new BufferedCharacteristic<SkinTemperature1sBuffered, SkinTemperature1sData>("99ebde23-1b3e-4084-85c2-18bca6eb5a1a", "Skin Temperature 1s Buffered", SkinTemperature1sBuffered.class);
	public static final BufferedCharacteristic<SkinTemperatureBuffered, SkinTemperatureData> SKIN_TEMPERATURE_BUFFERED = new BufferedCharacteristic<SkinTemperatureBuffered, SkinTemperatureData>("78663ddf-83c3-4665-9d04-003c990acf78", "Skin Temperature Buffered", SkinTemperatureBuffered.class);
	public static final Characteristic<SkinTemperatureWaiting> SKIN_TEMPERATURE_WAITING = new Characteristic<SkinTemperatureWaiting>("f89edeb7-0d8c-b529-baef-2f9ab82f6cc6", "Skin Temperature Waiting", SkinTemperatureWaiting.class);
	public static final Characteristic<Status> STATUS = new Characteristic<Status>("f89edec9-b0e0-d44f-45e8-d125177194d5", "Status", Status.class);
	public static final Characteristic<Steps> STEPS = new Characteristic<Steps>("8ba3207b-6a87-424d-bde0-4f665f500f04", "Steps", Steps.class);
	public static final BufferedCharacteristic<StepsBuffered, StepsData> STEPS_BUFFERED = new BufferedCharacteristic<StepsBuffered, StepsData>("58c6374e-9927-414a-b90e-475014af65ba", "Steps Buffered", StepsBuffered.class);
	public static final Characteristic<StepsWaiting> STEPS_WAITING = new Characteristic<StepsWaiting>("9b72b459-d1e5-48fe-9c91-2fb168261b21", "Steps Waiting", StepsWaiting.class);
	public static final Characteristic<TapMarker> TAP_MARKER = new Characteristic<TapMarker>("207b171c-d7a5-48ef-8e60-6ccb5f0993f4", "Tap Marker", TapMarker.class);
	public static final BufferedCharacteristic<ZcmBuffered, ZcmData> ZCM_BUFFERED = new BufferedCharacteristic<ZcmBuffered, ZcmData>("f89edebe-0136-7900-2cec-b4641bb709f5", "Zcm Buffered", ZcmBuffered.class);
	public static final Characteristic<ZcmWaiting> ZCM_WAITING = new Characteristic<ZcmWaiting>("f89edebd-c3bd-2d57-ba04-7bdeb96e7366", "Zcm Waiting", ZcmWaiting.class);

	private static UuidObjectMap<Characteristic<? extends AbstractAttribute>> characteristics = new UuidObjectMap<Characteristic<? extends AbstractAttribute>>();

	static
	{
		characteristics.put(ACC_MAGNITUDE);
		characteristics.put(AGE_FLOAT);
		characteristics.put(BATTERY_LEVEL_BUFFERED);
		characteristics.put(BATTERY_LEVEL_WAITING);
		characteristics.put(BODY_POSITION);
		characteristics.put(BODY_POSITION_BUFFERED);
		characteristics.put(BODY_POSITION_WAITING);
		characteristics.put(CHARGING);
		characteristics.put(CHARGING_BUFFERED);
		characteristics.put(CHARGING_WAITING);
		characteristics.put(COUNTER);
		characteristics.put(COUNTER_WAITING);
		characteristics.put(CURRENT_DRAIN);
		characteristics.put(CURRENT_DRAIN_BUFFERED);
		characteristics.put(CURRENT_DRAIN_WAITING);
		characteristics.put(CURRENT_TIME);
		characteristics.put(CUSTOM_DATA);
		characteristics.put(DATA_AVAILABLE);
		characteristics.put(DATA_RX);
		characteristics.put(DATA_TX);
		characteristics.put(DELETE_DATA);
		characteristics.put(ECG);
		characteristics.put(EDA_SCL_MEAN);
		characteristics.put(EDA_SCL_MEAN_BUFFERED);
		characteristics.put(EDA_SCL_MEAN_WAITING);
		characteristics.put(HR_MEAN);
		characteristics.put(HR_MEAN_BUFFERED);
		characteristics.put(HR_MEAN_WAITING);
		characteristics.put(HRV_IS_VALID);
		characteristics.put(HRV_IS_VALID_BUFFERED);
		characteristics.put(HRV_IS_VALID_WAITING);
		characteristics.put(INCLINATION);
		characteristics.put(INCLINATION_BUFFERED);
		characteristics.put(INCLINATION_WAITING);
		characteristics.put(LIGHT);
		characteristics.put(LIGHT_BUFFERED);
		characteristics.put(LIGHT_RGB);
		characteristics.put(LIGHT_RGB_BUFFERED);
		characteristics.put(LIGHT_RGB_WAITING);
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
		characteristics.put(RMSSD_BUFFERED);
		characteristics.put(RMSSD_WAITING);
		characteristics.put(SAVE_ENERGY);
		characteristics.put(SEND_BUFFERED_DATA);
		characteristics.put(SENSOR_LOCATION);
		characteristics.put(SENSOR_TEMPERATURE);
		characteristics.put(SENSOR_TEMPERATURE_BUFFERED);
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
		characteristics.put(ZCM_BUFFERED);
		characteristics.put(ZCM_WAITING);
	}

	/**
	 * Looks up the characteristic for the given UUID. If the UUID is not found, a lookup in the smartgattlib is performed.
	 * 
	 * @param uuid the UUID to look for
	 * @return the corresponding characteristic or the DEFAULT characteristic of smartgattlib if UUID is not found
	 */
	public static Characteristic<? extends AbstractAttribute> lookup(UUID uuid)
	{
		Characteristic<? extends AbstractAttribute> result = characteristics.get(uuid);
		if (result == null)
		{
			result = com.movisens.smartgattlib.Characteristics.lookup(uuid);
		}
		return result;
	}

}
