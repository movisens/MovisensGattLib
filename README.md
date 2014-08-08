MovisensGattLib
===============

MovisensGattLib is a Java library that simplifies the work with [movisens **Bluetooth SMART** sensors](http://www.movisens.com/en/products/) (a.k.a. **Bluetooth Low Energy** in Bluetooth 4.0). It provides all UUIDs of the movisens sensors and an convenient way to interpret the characteristics (e.g. MovementAcceleration, RMSS).

This library is build on the [SmartGattLib](https://github.com/movisens/SmartGattLib). Please read the documentation there as well.

There is also a complete [example project](https://github.com/movisens/MovisensGattSensorExample) on how to connect to the movisens Sensors.

### Set up ###

1. Download the latest .jar file from the [MovsiensGattLib releases](https://github.com/movisens/MovisensGattLib/releases) page and place it in your Android app’s libs/ folder.
2. Download the latest .jar file from the [SmartGattLib releases](https://github.com/movisens/SmartGattLib/releases) page and place it in your Android app’s libs/ folder.
3. Use the example below to identifiy services and characteristics and interpret their data

### Example Usage ###
```java
import com.movisens.smartgattlib.*;
import com.movisens.movisensgattlib.*;

// onConnected
// TODO: iterate over available services
UUID serviceUuid = null;// service.getUuid();
if (MovisensService.ACC_SERVICE.equals(serviceUuid)) {

	// TODO: iterate over characteristics
	UUID characteristicUuid = null;// characteristic.getUuid();
	if (MovisensCharacteristic.MOVEMENTACCELERATION.equals(characteristicUuid)) {
		// TODO: Enable notification of characteristic MovisensCharacteristic.MOVEMENTACCELERATION
	}
}else if (MovisensService.MOVISENS_SERVICE.equals(serviceUuid)) {
	byte[] enable = GattByteBuffer.allocate(1).putBoolean(true).array();
	
	// TODO: iterate over characteristics
	UUID characteristicUuid = null;// characteristic.getUuid();
	if (MovisensCharacteristic.CURRENT_TIME.equals(characteristicUuid)) {
		// TODO: Write getLocalTime() to characteristic MovisensCharacteristic.CURRENT_TIME to sync time
	}else if (MovisensCharacteristic.MEASUREMENT_ENABLED.equals(characteristicUuid)) {
		// TODO: Write enable to characteristic MovisensCharacteristic.MEASUREMENT_ENABLED to enable measurement
	}else if (MovisensCharacteristic.AUTHORIZE.equals(characteristicUuid)) {
		// TODO: Write enable to characteristic MovisensCharacteristic.MEASUREMENT_ENABLED to start measurement
	}
}else{
	System.out.println("Found unused Service: " + MovisensService.lookup(serviceUuid, "unknown"));
}


// onCharacteristicChanged
UUID characteristicUuid = null;// characteristic.getUuid();
if (MovisensCharacteristic.MOVEMENTACCELERATION.equals(characteristicUuid)) {
	byte[] value = null;// characteristic.getValue();
	MovementAcceleration movement = new MovementAcceleration(value);
	System.out.println("MovementAcceleration is: " + movement.getValue());
}
```
