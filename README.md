# MovisensGattLib

MovisensGattLib is a Java 11 library for movisens BLE GATT data. It contains
the movisens service and characteristic UUIDs, typed attributes for reading and
writing raw data, and security helpers for SPAKE2, pairing, and sealing.

The library is not a BLE stack. Scanning, connecting, service discovery, reads,
writes, and notifications are provided by the application.

## Dependency

```gradle
repositories {
    maven { url "https://jitpack.io" }
}

dependencies {
    implementation "com.github.movisens:MovisensGattLib:5.0.0"
}
```

`SmartGattLib` is a transitive dependency and provides the shared base classes
such as `AbstractAttribute`, `Characteristic`, and `CryptoManager`.

## BLE Stack Boundary

This README assumes that the BLE stack is connected through these three
methods:

```java
String getAdvertizedName();
AbstractAttribute processIncomingAttribute(UUID uuid, byte[] rawAttributeData);
void writeOutgoingAttribute(UUID uuid, byte[] rawAttributeData);
```

The movisens library does not know the stack directly. The application wraps
the stack in an adapter and translates between BLE raw data and typed
attributes there:

```java
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.smartgattlib.helper.AbstractAttribute;
import com.movisens.smartgattlib.helper.Characteristic;
import com.movisens.smartgattlib.security.CryptoManager;

public abstract class MovisensBleAdapter {
    protected final CryptoManager cryptoManager = new CryptoManager();

    protected MovisensBleAdapter() {
        cryptoManager.initialize();
    }

    public abstract String getAdvertizedName();

    public abstract void writeOutgoingAttribute(
        UUID uuid,
        byte[] rawAttributeData
    );

    public AbstractAttribute processIncomingAttribute(UUID uuid, byte[] rawAttributeData) {
        Characteristic<? extends AbstractAttribute> characteristic =
            MovisensCharacteristics.lookup(uuid);

        AbstractAttribute attribute =
            characteristic.createAttribute(cryptoManager, rawAttributeData);

        return attribute;
    }

    public void writeAttribute(AbstractAttribute attribute) {
        UUID uuid = attribute.getCharacteristic().getUuid();
        byte[] rawAttributeData = attribute.getOutgoingData(cryptoManager);
        writeOutgoingAttribute(uuid, rawAttributeData);
    }

    protected byte[] sensorIdFromAdvertizedName() {
        String name = getAdvertizedName();
        String serial = name.substring(name.lastIndexOf(' ') + 1);
        return serial.getBytes(StandardCharsets.US_ASCII);
    }
}
```

`processIncomingAttribute` is called for every BLE read result and every
notification. `MovisensCharacteristics.lookup(uuid)` finds movisens
characteristics and falls back to `SmartGattLib` for standard BLE
characteristics. `createAttribute(cryptoManager, rawAttributeData)` decrypts
automatically once `cryptoManager` has a session key.

`writeAttribute` is the reverse direction: a typed attribute uses
`getOutgoingData(cryptoManager)` to produce the bytes that the BLE stack writes
to the UUID. This also encrypts automatically once a session key is set.
Plaintext attributes such as PAKE data stay plaintext.

`writeOutgoingAttribute(...)` is only the BLE write. It is not a sensor
`CommandResult`. If the application needs the sensor status of a command, it
reads or receives `MovisensCharacteristics.COMMAND_RESULT` separately and
evaluates the generated `CommandResult` attribute.

## Services and Characteristics

During service discovery, compare UUIDs with the constants:

```java
if (MovisensServices.PHYSICAL_ACTIVITY.equals(serviceUuid)) {
    // Enable notifications for measured values, e.g. MOVEMENT_ACCELERATION.
}

if (MovisensCharacteristics.MOVEMENT_ACCELERATION.equals(characteristicUuid)) {
    // Enable notification for this characteristic.
}
```

Incoming values are typed:

```java
AbstractAttribute attribute = processIncomingAttribute(uuid, rawAttributeData);

if (attribute instanceof MovementAcceleration) {
    MovementAcceleration movement = (MovementAcceleration) attribute;
    double valueInG = movement.getMovementAcceleration();
}
```

Outgoing values are created as attributes and written:

```java
writeAttribute(new CurrentTimeMs(new Date()));
writeAttribute(new TimeZoneId(ZoneId.systemDefault().getId()));
writeAttribute(new StartMeasurement(3600L));
```

### Return Values via Command Result

Many writable movisens characteristics trigger a `command_result` after the BLE
write. The BLE write itself only confirms transport. The sensor status is
reported separately through `MovisensCharacteristics.COMMAND_RESULT`; the
application reads that characteristic after the write or handles its
notification and processes the value as `CommandResult`.
`CommandResult.getError()` returns the `EnumCommandResult`.

The `EnumCommandResult` values that a specific attribute can produce are
documented in the corresponding characteristic attribute class under
`com.movisens.movisensgattlib.attributes`. Look for
`This triggers a command_result notification` and `Possible results`. The list
is attribute-specific; do not apply it to all writes.

## Security, Pairing, and Sealing

Protected BLE attributes become usable only after a successful SPAKE2 session.
`SpakeSession` works with `SpakeGattConnection`. That interface is an
additional bridge for the PAKE flow, not the general write API:
`setAttribute(...)` must return the sensor `CommandResult` for the written PAKE
step.

For PAKE, every written step must return `OK`. `SpakeSession` aborts on any
other result with a `PakeException`; `PakeException.getResult()` then contains
the `EnumCommandResult` returned by the sensor. The characteristic
documentation lists the possible values per step:

- `PakeStart`: `OK`, `UNEXPECTED_EXCEPTION`, or a `PAKE_RATE_LIMITED_*`
  result (`PAKE_RATE_LIMITED_60_MIN` or `PAKE_RATE_LIMITED_24_H`) while a
  lockout is active.
- `PakeClientShare1` / `PakeClientShare2`: `OK`,
  `INVALID_PAKE_STATE`; on the fragment that completes the share, also
  `INVALID_POINT` or `UNEXPECTED_EXCEPTION`.
- `PakeClientConfirm1` / `PakeClientConfirm2`: `OK`,
  `INVALID_PAKE_STATE`; on the fragment that completes the confirmation, also
  `INVALID_POINT`, `WRONG_CODE`, or `KEY_CONFIRMATION_FAILED`.

In an application, the PAKE-specific results usually mean:
`WRONG_CODE` is a wrong colour code or a wrong sealing password,
`INVALID_PAKE_STATE` is a missing, aborted, or incorrectly sequenced PAKE
state, `INVALID_POINT` is invalid SPAKE2 point data or failed key derivation,
`KEY_CONFIRMATION_FAILED` is a failed sensor confirmation, and
`UNEXPECTED_EXCEPTION` is an internal sensor error. For `PAKE_RATE_LIMITED_*`,
PAKE is locked; map the suffix (`60_MIN` or `24_H`) to the wait time and do not
start an automatic retry.

An adapter can connect it like this:

```java
import java.util.UUID;

import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.movisensgattlib.attributes.CommandResult;
import com.movisens.movisensgattlib.attributes.EnumCommandResult;
import com.movisens.movisensgattlib.security.SpakeGattConnection;
import com.movisens.smartgattlib.helper.AbstractAttribute;
import com.movisens.smartgattlib.helper.Characteristic;

public final class SpakeBleConnection implements SpakeGattConnection {
    private final MovisensBleAdapter adapter;

    public SpakeBleConnection(MovisensBleAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public EnumCommandResult setAttribute(AbstractAttribute attribute) {
        adapter.writeAttribute(attribute);
        return readCommandResult();
    }

    @Override
    public <T extends AbstractAttribute> T getAttribute(Characteristic<T> characteristic) {
        byte[] rawAttributeData = readAttributeWithBleStack(characteristic.getUuid());
        return cast(adapter.processIncomingAttribute(characteristic.getUuid(), rawAttributeData));
    }

    private EnumCommandResult readCommandResult() {
        byte[] rawAttributeData =
            readAttributeWithBleStack(MovisensCharacteristics.COMMAND_RESULT.getUuid());
        CommandResult result = (CommandResult) adapter.processIncomingAttribute(
            MovisensCharacteristics.COMMAND_RESULT.getUuid(),
            rawAttributeData
        );
        return result.getError();
    }

    private byte[] readAttributeWithBleStack(UUID uuid) {
        // Stack-specific BLE read. The read result is then passed through
        // adapter.processIncomingAttribute(uuid, rawAttributeData).
        throw new UnsupportedOperationException("connect this to your BLE stack");
    }

    @SuppressWarnings("unchecked")
    private static <T extends AbstractAttribute> T cast(AbstractAttribute attribute) {
        return (T) attribute;
    }
}
```

The `sensorId` for SPAKE2 is the serial number from the BLE advertised name. It
must match the firmware byte-for-byte. In the usual movisens advertised names,
it is the last space-separated token of the name.

### Sealed Sensor

For an already sealed sensor, the secret is derived from the user password:

```java
byte[] sensorId = sensorIdFromAdvertizedName();
byte[] clientId = SpakeIdentities.clientId();
byte[] secret = SealingPassword.toSecret(password);

SpakeBleConnection spakeConnection = new SpakeBleConnection(this);
byte[] aesKey = SpakeSession.run(spakeConnection, sensorId, clientId, secret);
cryptoManager.setKey(aesKey);
```

If the password is wrong or the sensor is rate-limited after too many failed
attempts, `SpakeSession.run(...)` throws a `PakeException`. The contained
`EnumCommandResult` describes the sensor error, for example `WRONG_CODE` or
`PAKE_RATE_LIMITED_60_MIN`.

### Unsealed Sensor

For an unsealed sensor, the app first starts the PAKE session so the sensor can
show the colour code. The code read by the user is then converted to secret
bytes:

```java
SpakeBleConnection spakeConnection = new SpakeBleConnection(this);
SpakeSession.start(spakeConnection);

List<PairingColour> colours = Arrays.asList(
    PairingColour.RED,
    PairingColour.GREEN,
    PairingColour.BLUE,
    PairingColour.RED,
    PairingColour.GREEN,
    PairingColour.BLUE
);

byte[] sensorId = sensorIdFromAdvertizedName();
byte[] aesKey = SpakeSession.runExistingSession(
    spakeConnection,
    sensorId,
    SpakeIdentities.clientId(),
    PairingColour.toSecret(colours)
);
cryptoManager.setKey(aesKey);
```

After `cryptoManager.setKey(aesKey)`, the encrypted BLE channel is active. Only
then can the app seal the sensor with a persistent password:

```java
writeAttribute(new SealSensor(cryptoManager, password));
```
