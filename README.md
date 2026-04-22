MovisensGattLib
===============

<a href="https://jitpack.io/#movisens/MovisensGattLib/"><img src="https://img.shields.io/github/tag/movisens/MovisensGattLib.svg?label=Maven%20on%20JitPack" /></a>

MovisensGattLib is a Java library that simplifies the work with [movisens **Bluetooth SMART** sensors](http://www.movisens.com/en/products/) (a.k.a. **Bluetooth Low Energy** in Bluetooth 4.0). It provides all UUIDs of the movisens sensors and an convenient way to interpret the characteristics (e.g. MovementAcceleration, RMSS).

This library is build on the [SmartGattLib](https://github.com/movisens/SmartGattLib). Please read the documentation there as well.

There is also a complete [example project](https://github.com/movisens/MovisensGattSensorExample) on how to connect to the movisens Sensors.

### Set up ###

1. Add the JitPack repository and the dependency to your build file:

  ```gradle
	repositories {
	    maven { url "https://jitpack.io" }
	}
	dependencies {
	    compile 'com.github.movisens:SmartGattLib:3.6.1'
	    compile 'com.github.movisens:MovisensGattLib:2.16.0'
	}
  ```
  or download the latest .jar file from the [MovsiensGattLib releases](https://github.com/movisens/MovisensGattLib/releases) and the [SmartGattLib releases](https://github.com/movisens/SmartGattLib/releases) and place it in your Android app’s libs/ folder.
2. Use the example below to identifiy services and characteristics and interpret their data

### Example Usage ###
```java
import com.movisens.smartgattlib.*;
import com.movisens.smartgattlib.attributes.*;
import com.movisens.smartgattlib.helper.*;
import com.movisens.movisensgattlib.attributes.*;

// onConnected
// TODO: iterate over available services
UUID serviceUuid = null;// service.getUuid();
if (MovisensServices.PHYSICAL_ACTIVITY_SERVICE.equals(serviceUuid)) {

	// TODO: iterate over characteristics
	UUID characteristicUuid = null;// characteristic.getUuid();
	if (MovisensCharacteristics.MOVEMENT_ACCELERATION.equals(characteristicUuid)) {
		// TODO: Enable notification of characteristic MovisensCharacteristics.MOVEMENT_ACCELERATION
	}
}else if (MovisensServices.SENSOR_CONTROL_SERVICE.equals(serviceUuid)) {
	byte[] enable = GattByteBuffer.allocate(1).putBoolean(true).array();
	
    // TODO: iterate over characteristics
    UUID characteristicUuid = null;// characteristic.getUuid();
    if (MovisensCharacteristics.CURRENT_TIME.equals(characteristicUuid)) {
        // TODO: Write getLocalTime() to characteristic MovisensCharacteristic.CURRENT_TIME to sync time
    }else if (MovisensCharacteristics.MEASUREMENT_ENABLED.equals(characteristicUuid)) {
        // TODO: Write enable to characteristic MovisensCharacteristic.MEASUREMENT_ENABLED to enable measurement
    }else if (MovisensCharacteristics.SAVE_ENERGY.equals(characteristicUuid)) {
        // TODO: Write enable to characteristic MovisensCharacteristic.characteristic to go into energy saving mode
    }
}else{
	System.out.println("Found unused Service: " + MovisensServices.lookup(serviceUuid, "unknown"));
}


// onCharacteristicChanged
UUID uuid = null; // TODO: Fill with the received uuid
byte[] data = null; // TODO: Fill with the received bytes

AbstractAttribute a = Characteristics.lookup(uuid).createAttribute(data);
if (a instanceof MovementAcceleration) {
    MovementAcceleration movementAcceleration = ((MovementAcceleration) a);
    System.out.println("Received MovementAcceleration: " + movementAcceleration.getMovementAcceleration());
}
```

### Sealed Sensor Security ###

If a sensor is sealed, protected BLE characteristics are no longer accessible immediately after connect.

At application level, the mechanism is meant to do two things:

- protect sensitive BLE reads and writes against unauthorized access
- protect the application-level BLE session against passive eavesdropping and, for sealed sensors, against a man-in-the-middle between app and sensor

What this means for an application:

1. Connect to the sensor as usual.
2. Check whether the sensor is sealed.
3. Start the secure BLE session.
4. If the sensor is sealed, ask the user for the sealing password and authenticate the session.
5. Continue reading and writing protected attributes like before.

From the user's point of view in an app such as SensorManager, the flow is simply:

- connect to sensor
- if the sensor is sealed, prompt for password
- after successful authentication, continue with the normal workflow

The important point is that application code should establish the secure session once after connect. After that, normal attribute access continues unchanged.

Minimal example (stack-agnostic Java integration example):

```java
import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.movisensgattlib.attributes.AuthConfirm;
import com.movisens.movisensgattlib.attributes.EnumCommandResult;
import com.movisens.movisensgattlib.attributes.Login;
import com.movisens.movisensgattlib.attributes.SensorSealed;
import com.movisens.movisensgattlib.attributes.TimeZoneId;
import com.movisens.movisensgattlib.security.KeyExchangeManager;
import com.movisens.smartgattlib.helper.AbstractAttribute;
import com.movisens.smartgattlib.helper.AbstractReadAttribute;
import com.movisens.smartgattlib.security.CryptoManager;

// bleConnection is an application-specific BLE transport wrapper.
// This is not tied to a specific platform such as Android or desktop Java.
// It should use the provided CryptoManager when reading and writing attributes.

CryptoManager cryptoManager = new CryptoManager();
cryptoManager.initialize();

SensorSealed sensorSealed = bleConnection.getAttribute(MovisensCharacteristics.SENSOR_SEALED);

// Start the secure BLE session.
KeyExchangeManager keyExchangeManager = new KeyExchangeManager();
for (AbstractAttribute request : keyExchangeManager.getRequestAttributes()) {
    bleConnection.setAttribute(request);
}

AbstractReadAttribute[] response = new AbstractReadAttribute[] {
    bleConnection.getAttribute(MovisensCharacteristics.KEY_EXCHANGE_RESPONSE_1),
    bleConnection.getAttribute(MovisensCharacteristics.KEY_EXCHANGE_RESPONSE_2)
};

cryptoManager.setKey(keyExchangeManager.getAesKey(response));

if (sensorSealed.getValue()) {
    Login login = new Login(cryptoManager, keyExchangeManager, "secret");
    EnumCommandResult loginResult = bleConnection.setAttribute(login);
    if (loginResult != EnumCommandResult.OK) {
        throw new IllegalStateException("BLE login failed: " + loginResult);
    }

    AuthConfirm authConfirm = bleConnection.getAttribute(MovisensCharacteristics.AUTH_CONFIRM);
    if (!login.isAuthConfirmValid(authConfirm.getRawData())) {
        throw new IllegalStateException("sensor auth confirmation does not match the negotiated session");
    }
}

// Protected attributes can now be used normally.
bleConnection.setAttribute(new TimeZoneId("Europe/Berlin"));
```

If you already use one of our existing BLE connection wrappers, transport-level encryption handling is typically already wired in. In that case the application only has to establish the secure session after connect and, for sealed sensors, handle the password prompt and login step.
