# Changelog

## [2.14.0]

- added characteristics for application level encryption
- added characteristics for sealing and unsealing
- added characteristics StopMeasurement so stop a measurement instead of setting MeasuremendEnabled to false
- added characteristic BatteryVoltage
- added characteristic EdaLola to stream eda data with low lattency
- added characteristic to handle sensor evaluation

## [2.9.0]

- added characteristic MeasurementStatus

## [2.8.0]

- added characteristic StorageLevel

## [2.7.1]

- added characteristic TimeZone and TimeOffset

## [2.4.0] (2019-09-03)

- added CURRENT_TIME_MS to set or get sensor time in milliseconds

## [2.3.0] (2019-04-17)

- added START_MEASUREMENT to start the sensor and set the measurement duration

## [2.2.0] (2019-02-21)

- added some attributes: RESPIRATORY_MOVEMENT, CUSTOM_DATA, ACTIVATED_BUFFERED_CHARACTERISTICS
- removed experimental attributes (e.g. ECG, ILLUMINATION)

## [2.1.0] (2019-02-21)

- introduced AbstractBufferedAttribute, AbstractData, BufferedCharacteristic
- use generics in Characteristic

## [2.0.0](https://github.com/movisens/SmartGattLib/compare/v1.4.0...v2.0.0) (2017-11-07)

This release comes with a significant api change to simplify future addons.

### Upgrade Instructions

* replace ```com.movisens.movisensgattlib.MovisensServices``` with ```com.movisens.movisensgattlib.MovisensServices```
* replace ```com.movisens.movisensgattlib.MovisensCharacteristic``` with ```com.movisens.movisensgattlib.MovisensCharacteristics```

See [SmartGattLib Changelog](https://github.com/movisens/SmartGattLib/blob/master/CHANGELOG.md#300-2017-11-07) for further instructions.