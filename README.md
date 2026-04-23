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
	    compile 'com.github.movisens:SmartGattLib:4.0.0'
	    compile 'com.github.movisens:MovisensGattLib:3.0.0'
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

### BLE Security For Sealed And Unsealed Sensors ###

If a sensor is already sealed, protected BLE characteristics are no longer accessible immediately after connect.

At application level, the mechanism is meant to do two things:

- protect sensitive BLE reads and writes against unauthorized access
- protect the application-level BLE session against passive eavesdropping and, for sealed sensors, against a man-in-the-middle between app and sensor

What this means for an application:

1. Connect to the sensor as usual.
2. Check whether the sensor is sealed.
3. Start the secure BLE session.
4. If the sensor is already sealed, ask the user for the sealing password and authenticate the session.
5. If the sensor is not sealed yet, complete the temporary LED pairing-code authentication and then seal the sensor over BLE.
6. Continue with the normal protected workflow for the current state.

From the user's point of view in an app such as SensorManager, the flow is simply:

- connect to sensor
- if the sensor is already sealed, prompt for password
- if the sensor is not sealed yet, complete the temporary LED pairing-code step before sealing
- after successful authentication or sealing, continue with the normal workflow

The important point is that application code should establish the secure session once after connect. After that, normal attribute access continues unchanged.

There are two application-level flows after the key exchange:

- Already sealed sensor:
  authenticate with the persistent sealing password by writing `LOGIN`, then read `AUTH_CONFIRM` and verify it before treating the BLE session as authenticated.
- Not sealed yet:
  complete the temporary LED pairing-code verification first, again read and verify `AUTH_CONFIRM`, and only then write `SEAL_SENSOR` with the new persistent sealing password/key.

Minimal example for both cases (stack-agnostic Java integration example):

```java
import com.movisens.movisensgattlib.MovisensCharacteristics;
import com.movisens.movisensgattlib.attributes.AuthConfirm;
import com.movisens.movisensgattlib.attributes.EnumCommandResult;
import com.movisens.movisensgattlib.attributes.Login;
import com.movisens.movisensgattlib.attributes.SealSensor;
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
} else {
    // The user must read the current 6-symbol LED pairing code from the sensor.
    // Digit mapping is: 0=red, 1=green, 2=blue, 3=white, 4=yellow.
    int[] pairingCodeDigits = readPairingCodeFromUser();

    Login pairingLogin = new Login(cryptoManager, keyExchangeManager, pairingCodeDigits);
    EnumCommandResult pairingLoginResult = bleConnection.setAttribute(pairingLogin);
    if (pairingLoginResult != EnumCommandResult.OK) {
        throw new IllegalStateException("BLE pairing-code login failed: " + pairingLoginResult);
    }

    AuthConfirm authConfirm = bleConnection.getAttribute(MovisensCharacteristics.AUTH_CONFIRM);
    if (!pairingLogin.isAuthConfirmValid(authConfirm.getRawData())) {
        throw new IllegalStateException("sensor auth confirmation does not match the negotiated session");
    }

    EnumCommandResult sealResult = bleConnection.setAttribute(new SealSensor(cryptoManager, "secret"));
    if (sealResult != EnumCommandResult.OK) {
        throw new IllegalStateException("BLE sealing failed: " + sealResult);
    }
}

// Protected attributes can now be used normally.
bleConnection.setAttribute(new TimeZoneId("Europe/Berlin"));
```

For a sensor that is not sealed yet, the flow is different after the key exchange:

1. The sensor starts a temporary 6-symbol LED pairing-code blinker.
2. The application reads that code from the user.
3. The application creates `Login` from the pairing-code digits instead of the persistent password.
4. The application writes `LOGIN`.
5. The application reads `AUTH_CONFIRM` and verifies it with `login.isAuthConfirmValid(...)`.
6. Only after that temporary MITM verification succeeds, the application writes `SEAL_SENSOR` with the new persistent sealing password/key.

The temporary pairing-code login uses the same transcript-bound proof format as the sealed-sensor login. A successful temporary pairing proof does not yet mean that the sensor is sealed. It only authorizes the current BLE session to perform the sealing step.

If you already use one of our existing BLE connection wrappers, transport-level encryption handling is typically already wired in. In that case the application only has to establish the secure session after connect and then either handle the sealed-sensor password login or the unsealed-sensor pairing-plus-sealing flow.
