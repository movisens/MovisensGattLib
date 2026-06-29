# Changelog

## 5.0.0

- replaced the legacy BLE Diffie-Hellman login with the balanced SPAKE2 PAKE pairing flow, including PAKE attributes, pairing-code and sealed-session helpers, and test fixtures
- removed the legacy `LOGIN`, `AUTH_CONFIRM`, `KEY_EXCHANGE_REQUEST_1`, `KEY_EXCHANGE_REQUEST_2`, `KEY_EXCHANGE_RESPONSE_1`, and `KEY_EXCHANGE_RESPONSE_2` characteristics, plus the related `BleLoginCrypto` and `KeyExchangeManager` APIs
- removed the deprecated `MEASUREMENT_ENABLED` characteristic
- changed `SealSensor` from `SealSensor(CryptoManager, String)` to `SealSensor(Long)`; use `SealSensorBuilder.create(CryptoManager, String)` to create sealing writes from a password
- changed `DeleteData` and `SendBufferedData` from Boolean writes to parameterless command writes
- changed `EnumCommandResult` to match the current firmware status codes; removed the legacy `LOGIN_KEY_INVALID` and `KEY_EXCHANGE_FAILED` results and changed `getName()`/`toString()` strings for values 0 through 7 from lowercase to firmware-style uppercase
- added `SealSensorBuilder` to create `SealSensor` writes from an active encrypted crypto context and sealing password
- added `CustomData()` with a one-byte zero payload
- added fixed-length validation for generated PAKE, sealing, unsealing, and encryption write attributes
- documented command-result notifications on writable attributes
- fixed the OSGi bundle symbolic name and exported all public API packages
- added correctly spelled buffered-data and sample-rate accessors while keeping the old misspelled methods as deprecated aliases


## 3.0.1

- `SealSensor` now uses the encrypted BLE session instead of the plain-text path.

## 3.0.0

- upgraded the public `SmartGattLib` dependency to `4.0.0`
- updated the BLE login and key exchange implementation to the current crypto stack
- clarified and stabilized the BLE pairing, login, and sealing flow

## 2.16.0

- added BLE session authentication for sealed sensors
- introduced `AUTH_CONFIRM` and extended the BLE login flow so applications can verify that the sensor participated in the authenticated session
- improved protection of sealed BLE sessions against man-in-the-middle attacks while keeping the normal application workflow unchanged after login

## 2.14.0

- added characteristics for application level encryption
- added characteristics for sealing and unsealing
- added characteristics StopMeasurement so stop a measurement instead of setting MeasuremendEnabled to false
- added characteristic BatteryVoltage
- added characteristic EdaLola to stream eda data with low lattency
- added characteristic to handle sensor evaluation

## 2.13.0

- added command resutls for measurement start

## 2.12.0

- added possibility to set required characteristics (eg. met needs weight to be set)

## 2.11.0

- added command result

## 2.9.0

- added characteristic MeasurementStatus

## 2.8.0

- added characteristic StorageLevel

## 2.7.1

- added characteristic TimeZone and TimeOffset

## 2.4.0

- added CURRENT_TIME_MS to set or get sensor time in milliseconds

## 2.3.0

- added START_MEASUREMENT to start the sensor and set the measurement duration

## 2.2.0

- added some attributes: RESPIRATORY_MOVEMENT, CUSTOM_DATA, ACTIVATED_BUFFERED_CHARACTERISTICS
- removed experimental attributes (e.g. ECG, ILLUMINATION)

## 2.1.0

- introduced AbstractBufferedAttribute, AbstractData, BufferedCharacteristic
- use generics in Characteristic

## 2.0.0

- started changelog
