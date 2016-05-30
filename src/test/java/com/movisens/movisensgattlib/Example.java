package com.movisens.movisensgattlib;

import java.util.Date;
import java.util.UUID;

import com.movisens.movisensgattlib.characteristics.MovementAcceleration;
import com.movisens.smartgattlib.GattByteBuffer;

public class Example {

	public static void main(String[] args) {
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
	}

	private byte[] getLocalTime() {
		long time = new Date().getTime();
		while ((time % 1000) > 5) {
			time = new Date().getTime();
		}
		GattByteBuffer timeBB = GattByteBuffer.allocate(4);
		return 	timeBB.putUint32(time / 1000).array();
	}
}
