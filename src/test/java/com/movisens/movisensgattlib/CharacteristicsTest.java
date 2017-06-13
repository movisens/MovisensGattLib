package com.movisens.movisensgattlib;

import com.movisens.movisensgattlib.characteristics.AgeFloat;
import com.movisens.movisensgattlib.characteristics.BodyPosition;
import com.movisens.movisensgattlib.characteristics.DataAvailable;
import com.movisens.movisensgattlib.characteristics.HrvIsValid;
import com.movisens.movisensgattlib.characteristics.MeasurementEnabled;
import com.movisens.movisensgattlib.characteristics.MetLevel;
import com.movisens.movisensgattlib.characteristics.MovementAcceleration;
import com.movisens.movisensgattlib.characteristics.Rmssd;
import com.movisens.movisensgattlib.characteristics.SensorLocation;
import com.movisens.movisensgattlib.characteristics.StepCount;
import com.movisens.smartgattlib.GattByteBuffer;

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
        assertTrue(converted.getValue().equals(testAge));
    }

    @Test
    public void testBodyPosition() {
        for (int i = 0; i <= 9; i++) {
            BodyPosition.Position position = i != 8 ? BodyPosition.Position.getSensorPositionByValue(i) : BodyPosition.Position.NOT_WORN;
            GattByteBuffer gattByteBuffer = GattByteBuffer.allocate(4);
            BodyPosition copy = new BodyPosition(
                    gattByteBuffer.putUint8((short) position.value).array());
            assertEquals(copy.getValue(), position);
        }
    }

    @Test
    public void testSensorLocation() {
        for (int i = 0; i <= 11; i++) {
            SensorLocation.Position position = SensorLocation.Position.getSensorPositionByValue(i);
            SensorLocation sensorLocation = new SensorLocation(position);
            SensorLocation copySensorLocation = new SensorLocation(sensorLocation.getBytes());
            assertEquals(position, copySensorLocation.getValue());
        }
    }

    @Test
    public void testMetLevel() {
        Integer[] metLevel = new Integer[]{12, 25, 45, 18};
        GattByteBuffer gattByteBuffer = GattByteBuffer.allocate(4);
        for (int i : metLevel) {
            gattByteBuffer.putUint8((short) i);
        }
        MetLevel level = new MetLevel(gattByteBuffer.array());
        assertTrue(level.isValid());
        assertArrayEquals(metLevel, level.getValue());
        assertEquals((int) metLevel[0], level.getSedentaryValue());
        assertEquals((int) metLevel[1], level.getLightValue());
        assertEquals((int) metLevel[2], level.getModerateValue());
        assertEquals((int) metLevel[3], level.getVigorousValue());
    }

    @Test
    public void testDataAvailable() {
        boolean dataAvailable = true;
        GattByteBuffer gattByteBuffer = GattByteBuffer.allocate(4);
        gattByteBuffer.putUint8((short) (dataAvailable ? 1 : 0));
        DataAvailable available = new DataAvailable(gattByteBuffer.array());
        assertEquals(dataAvailable, available.getValue());
    }

    @Test
    public void testHrvIsValid() {
        boolean hrvIsValid = true;
        GattByteBuffer gattByteBuffer = GattByteBuffer.allocate(4);
        gattByteBuffer.putUint8((short) (hrvIsValid ? 1 : 0));
        HrvIsValid available = new HrvIsValid(gattByteBuffer.array());
        assertEquals(hrvIsValid, available.getValue());
    }

    @Test
    public void testMeasurementEnabled() {
        boolean measurementEnabled = true;
        GattByteBuffer gattByteBuffer = GattByteBuffer.allocate(4);
        gattByteBuffer.putUint8((short) (measurementEnabled ? 1 : 0));
        MeasurementEnabled available = new MeasurementEnabled(gattByteBuffer.array());
        assertEquals(measurementEnabled, available.getValue());
    }

    @Test
    public void testMovementAcceleration() {
        Double lsbValue = 1.0 / 256; // [g]
        Double acc = 0.75;
        GattByteBuffer gattByteBuffer = GattByteBuffer.allocate(4);
        gattByteBuffer.putUint16(new Double(acc / lsbValue).intValue());
        MovementAcceleration movementAcceleration = new MovementAcceleration(gattByteBuffer.array());
        assertEquals(acc, movementAcceleration.getValue());
    }

    @Test
    public void testRmssd() {
        Double rmssd = 125d;
        GattByteBuffer gattByteBuffer = GattByteBuffer.allocate(4);
        gattByteBuffer.putInt16((short) rmssd.intValue());
        Rmssd copyRmssd = new Rmssd(gattByteBuffer.array());
        assertTrue(copyRmssd.isValid());
        assertEquals(rmssd, copyRmssd.getValue());
    }

    @Test
    public void testStepCount() {
        Integer steps = 121;
        GattByteBuffer gattByteBuffer = GattByteBuffer.allocate(4);
        gattByteBuffer.putUint16(steps);
        StepCount stepCount = new StepCount(gattByteBuffer.array());
        assertTrue(stepCount.isValid());
        assertEquals(steps, stepCount.getValue());
    }

}
