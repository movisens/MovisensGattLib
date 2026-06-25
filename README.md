# MovisensGattLib

MovisensGattLib ist eine Java-11-Bibliothek für movisens BLE-GATT-Daten. Sie
enthält die movisens Service- und Characteristic-UUIDs, typisierte Attribute zum
Lesen und Schreiben der Rohdaten und die Security-Helfer für SPAKE2, Pairing und
Sealing.

Die Bibliothek ist kein BLE-Stack. Scannen, Verbinden, Service Discovery, Reads,
Writes und Notifications kommen aus der Anwendung.

## Dependency

```gradle
repositories {
    maven { url "https://jitpack.io" }
}

dependencies {
    implementation "com.github.movisens:MovisensGattLib:4.0.0"
}
```

`SmartGattLib` ist eine transitive Dependency und liefert die gemeinsamen
Basisklassen wie `AbstractAttribute`, `Characteristic` und `CryptoManager`.

## BLE-Stack-Grenze

Diese README setzt voraus, dass der unbekannte BLE-Stack über diese drei
Methoden angebunden wird:

```java
String getAdvertizedName();
AbstractAttribute processIncomingAttribute(UUID uuid, byte[] rawAttributeData);
void writeOutgoingAttribute(UUID uuid, byte[] rawAttributeData);
```

Die movisens Library kennt den Stack nicht direkt. Die Anwendung kapselt den
Stack in einem Adapter und übersetzt dort zwischen BLE-Rohdaten und typisierten
Attributen:

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

`processIncomingAttribute` wird für jedes BLE-Read-Result und jede Notification
aufgerufen. `MovisensCharacteristics.lookup(uuid)` findet movisens
Characteristics und fällt bei Standard-BLE-Characteristics auf `SmartGattLib`
zurück. `createAttribute(cryptoManager, rawAttributeData)` entschlüsselt
automatisch, sobald `cryptoManager` einen Session-Key hat.

`writeAttribute` ist die Gegenrichtung: Ein typisiertes Attribut erzeugt über
`getOutgoingData(cryptoManager)` die Bytes, die der BLE-Stack an die UUID
schreibt. Auch hier wird automatisch verschlüsselt, sobald ein Session-Key
gesetzt ist. Plaintext-Attribute wie PAKE-Daten bleiben Plaintext.

`writeOutgoingAttribute(...)` ist nur der BLE-Write. Es ist kein Sensor-
`CommandResult`. Wenn die Anwendung den Sensorstatus eines Kommandos braucht,
liest oder empfängt sie `MovisensCharacteristics.COMMAND_RESULT` separat und
wertet das daraus erzeugte `CommandResult`-Attribut aus.

## Services und Characteristics

Bei Service Discovery vergleichst du die UUIDs mit den Konstanten:

```java
if (MovisensServices.PHYSICAL_ACTIVITY.equals(serviceUuid)) {
    // Notifications für Messwerte aktivieren, z. B. MOVEMENT_ACCELERATION.
}

if (MovisensCharacteristics.MOVEMENT_ACCELERATION.equals(characteristicUuid)) {
    // Notification für diese Characteristic aktivieren.
}
```

Eingehende Werte werden typisiert:

```java
AbstractAttribute attribute = processIncomingAttribute(uuid, rawAttributeData);

if (attribute instanceof MovementAcceleration) {
    MovementAcceleration movement = (MovementAcceleration) attribute;
    double valueInG = movement.getMovementAcceleration();
}
```

Ausgehende Werte werden als Attribut erstellt und geschrieben:

```java
writeAttribute(new CurrentTimeMs(new Date()));
writeAttribute(new TimeZoneId(ZoneId.systemDefault().getId()));
writeAttribute(new StartMeasurement(3600L));
```

## Security, Pairing und Sealing

Geschützte BLE-Attribute werden erst nach einer erfolgreichen SPAKE2-Session
nutzbar. `SpakeSession` arbeitet mit `SpakeGattConnection`. Diese Schnittstelle
ist eine zusätzliche Brücke für den PAKE-Flow, nicht die allgemeine
Schreib-API: `setAttribute(...)` muss den Sensor-`CommandResult` für den
geschriebenen PAKE-Schritt liefern.

Ein Adapter kann das so anbinden:

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
        // Stack-spezifischer BLE-Read. Das gelesene Ergebnis geht danach durch
        // adapter.processIncomingAttribute(uuid, rawAttributeData).
        throw new UnsupportedOperationException("connect this to your BLE stack");
    }

    @SuppressWarnings("unchecked")
    private static <T extends AbstractAttribute> T cast(AbstractAttribute attribute) {
        return (T) attribute;
    }
}
```

Die `sensorId` für SPAKE2 ist die Seriennummer aus dem BLE Advertized Name. Sie
muss bytegenau zur Firmware passen. In den üblichen movisens Advertized Names
ist sie der letzte Leerzeichen-getrennte Token des Namens.

### Sealed Sensor

Bei einem bereits sealed Sensor ist das Secret aus dem Benutzerpasswort
abgeleitet:

```java
byte[] sensorId = sensorIdFromAdvertizedName();
byte[] clientId = SpakeIdentities.clientId();
byte[] secret = SealingPassword.toSecret(password);

SpakeBleConnection spakeConnection = new SpakeBleConnection(this);
byte[] aesKey = SpakeSession.run(spakeConnection, sensorId, clientId, secret);
cryptoManager.setKey(aesKey);
```

Wenn das Passwort falsch ist oder der Sensor wegen zu vieler Fehlversuche
limitiert, wirft `SpakeSession.run(...)` eine `PakeException`. Der enthaltene
`EnumCommandResult` beschreibt den Sensorfehler, z. B. `WRONG_CODE` oder
`PAKE_RATE_LIMITED_60_MIN`.

### Unsealed Sensor

Bei einem unsealed Sensor startet die App zuerst die PAKE-Session, damit der
Sensor den Farbcode anzeigen kann. Danach wird der vom Benutzer gelesene Code in
Secret-Bytes übersetzt:

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

Nach `cryptoManager.setKey(aesKey)` ist der verschlüsselte BLE-Kanal aktiv. Erst
dann kann die App den Sensor mit einem persistenten Passwort sealen:

```java
writeAttribute(new SealSensor(cryptoManager, password));
```
