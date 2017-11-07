package com.movisens.movisensgattlib;


import com.movisens.movisensgattlib.attributes.*;
import com.movisens.smartgattlib.helper.GattByteBuffer;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CharacteristicsTest {
    @Test
    public void testAgeFloat() {
        Float testAge = 12.5F;
        AgeFloat ageFloat = new AgeFloat(testAge);
        AgeFloat converted = new AgeFloat(ageFloat.getBytes());
        assertTrue(converted.getAge().equals(testAge));
    }

    @Test
    public void testBodyPosition() {
        for (short i = 0; i <= 9; i++) {
            EnumBodyPosition position = i != 8 ? EnumBodyPosition.getByValue(i) : EnumBodyPosition.NOT_WORN;
            GattByteBuffer gattByteBuffer = GattByteBuffer.allocate(4);
            BodyPosition copy = new BodyPosition(
                    gattByteBuffer.putUint8((short) position.getValue()).array());
            assertEquals(copy.getBodyPosition(), position);
        }
    }

    @Test
    public void testSensorLocation() {
        for (short i = 0; i <= 11; i++) {
            EnumSensorLocation location = EnumSensorLocation.getByValue(i);
            SensorLocation sensorLocation = new SensorLocation(location);
            SensorLocation copySensorLocation = new SensorLocation(sensorLocation.getBytes());
            assertEquals(location, copySensorLocation.getSensorLocation());
        }
    }

    @Test
    public void testMetLevel() {
        Short[] metLevel = new Short[]{12, 25, 45, 18};
        GattByteBuffer gattByteBuffer = GattByteBuffer.allocate(4);
        for (int i : metLevel) {
            gattByteBuffer.putUint8((short) i);
        }
        MetLevel level = new MetLevel(gattByteBuffer.array());
        assertEquals(metLevel[0], level.getSedentary());
        assertEquals(metLevel[1], level.getLight());
        assertEquals(metLevel[2], level.getModerate());
        assertEquals(metLevel[3], level.getVigorous());
    }

    @Test
    public void testDataAvailable() {
        boolean dataAvailable = true;
        GattByteBuffer gattByteBuffer = GattByteBuffer.allocate(4);
        gattByteBuffer.putUint8((short) (dataAvailable ? 1 : 0));
        DataAvailable available = new DataAvailable(gattByteBuffer.array());
        assertEquals(dataAvailable, available.getDataAvailable());
    }

    @Test
    public void testHrvIsValid() {
        Short hrvIsValid = 1;
        GattByteBuffer gattByteBuffer = GattByteBuffer.allocate(4);
        gattByteBuffer.putUint8((short) hrvIsValid);
        HrvIsValid available = new HrvIsValid(gattByteBuffer.array());
        assertEquals(hrvIsValid, available.getHrvIsValid());
    }

    @Test
    public void testMeasurementEnabled() {
        boolean measurementEnabled = true;
        GattByteBuffer gattByteBuffer = GattByteBuffer.allocate(4);
        gattByteBuffer.putUint8((short) (measurementEnabled ? 1 : 0));
        MeasurementEnabled available = new MeasurementEnabled(gattByteBuffer.array());
        assertEquals(measurementEnabled, available.getMeasurementEnabled());
    }

    @Test
    public void testMovementAcceleration() {
        Double lsbValue = 1.0 / 256; // [g]
        Double acc = 0.75;
        GattByteBuffer gattByteBuffer = GattByteBuffer.allocate(4);
        gattByteBuffer.putUint16(new Double(acc / lsbValue).intValue());
        MovementAcceleration movementAcceleration = new MovementAcceleration(gattByteBuffer.array());
        assertEquals(acc, movementAcceleration.getMovementAcceleration());
    }

    @Test
    public void testRmssd() {
        Short rmssd = 125;
        GattByteBuffer gattByteBuffer = GattByteBuffer.allocate(4);
        gattByteBuffer.putInt16((short) rmssd.intValue());
        Rmssd copyRmssd = new Rmssd(gattByteBuffer.array());
        assertEquals(rmssd, copyRmssd.getRmssd());
    }

    @Test
    public void testStepCount() {
        Integer steps = 121;
        GattByteBuffer gattByteBuffer = GattByteBuffer.allocate(4);
        gattByteBuffer.putUint16(steps);

        Steps stepsCount = new Steps(gattByteBuffer.array());
        assertEquals(steps, stepsCount.getSteps());
    }

}
