package com.movisens.movisensgattlib;

import java.util.Date;
import java.util.UUID;
import java.util.Vector;

import com.movisens.movisensgattlib.attributes.CurrentDrainData;
import com.movisens.movisensgattlib.attributes.CurrentTime;
import com.movisens.movisensgattlib.attributes.MeasurementEnabled;
import com.movisens.movisensgattlib.attributes.MovementAcceleration;
import com.movisens.movisensgattlib.attributes.SaveEnergy;
import com.movisens.movisensgattlib.helper.AbstractBufferedAttribute;
import com.movisens.movisensgattlib.helper.AbstractData;
import com.movisens.movisensgattlib.helper.BufferedCharacteristic;
import com.movisens.smartgattlib.Characteristics;
import com.movisens.smartgattlib.attributes.DefaultAttribute;
import com.movisens.smartgattlib.helper.AbstractAttribute;
import com.movisens.smartgattlib.helper.GattByteBuffer;

public class Example
{

    public static void bla()
    {
        UUID uuid = null;
        byte[] value = null;
        AbstractAttribute ap = MovisensCharacteristics.lookup(uuid).createAttribute(value);

        if (ap instanceof AbstractBufferedAttribute<?>)
        {
            @SuppressWarnings({ "unused", "unchecked"})
            Iterable<AbstractData> iad = (Iterable<AbstractData>) ((AbstractBufferedAttribute<?>) ap).getData();
        }

    }

    public static <T extends AbstractAttribute, I extends AbstractData> Iterable<I> get(BufferedCharacteristic<T, I> c)
    {
        Vector<AbstractData> attributes = new Vector<AbstractData>();
        Vector<I> ts = new Vector<I>();

        for (AbstractData a : attributes)
        {
            if (a.getCharacteristic().equals(c))
            {
                @SuppressWarnings("unchecked")
                I ad = (I) a;
                ts.add(ad);
            }
        }

        return ts;
    }

    public static void test()
    {
        @SuppressWarnings("unused")
        Iterable<CurrentDrainData> d = get(MovisensCharacteristics.CURRENT_DRAIN_BUFFERED);
    }

    @SuppressWarnings("unused")
    public static void main(String[] args)
    {
        // onConnected
        // TODO: iterate over available services
        UUID serviceUuid = null;// service.getUuid();
        if (MovisensServices.PHYSICAL_ACTIVITY.equals(serviceUuid))
        {

            // TODO: iterate over characteristics
            UUID characteristicUuid = null;// characteristic.getUuid();
            if (MovisensCharacteristics.MOVEMENT_ACCELERATION.equals(characteristicUuid))
            {
                // TODO: Enable notification of characteristic MovisensCharacteristic.MOVEMENTACCELERATION
            }
        }
        else if (MovisensServices.SENSOR_CONTROL.equals(serviceUuid))
        {
            byte[] enable = GattByteBuffer.allocate(1).putBoolean(true).array();

            // TODO: iterate over characteristics
            UUID characteristicUuid = null;// characteristic.getUuid();
            if (MovisensCharacteristics.CURRENT_TIME.equals(characteristicUuid))
            {
                CurrentTime currentTime = new CurrentTime(getLocalTime());
                // TODO: Write currentTime.getBytes() to the characteristic currentTime.getCharacteristic().getUuid() to sync
                // time
            }
            else if (MovisensCharacteristics.MEASUREMENT_ENABLED.equals(characteristicUuid))
            {
                MeasurementEnabled measurementEnabled = new MeasurementEnabled(true);
                // TODO: Write measurementEnabled.getBytes() to the characteristic
                // measurementEnabled.getCharacteristic().getUuid() to enable measurement
            }
            else if (MovisensCharacteristics.SAVE_ENERGY.equals(characteristicUuid))
            {
                SaveEnergy saveEnergy = new SaveEnergy(true);
                // TODO: Write saveEnergy.getBytes() to the characteristic saveEnergy.getCharacteristic().getUuid() to go into
                // energy saving mode
            }
        }
        else
        {
            System.out.println("Found unused Service: " + MovisensServices.lookup(serviceUuid));
        }

        // onCharacteristicChanged
        UUID uuid = null; // TODO: Fill with the received uuid
        byte[] data = null; // TODO: Fill with the received bytes

        AbstractAttribute a = Characteristics.lookup(uuid).createAttribute(data);
        if (a instanceof MovementAcceleration)
        {
            MovementAcceleration movementAcceleration = ((MovementAcceleration) a);
            System.out.println("Received MovementAcceleration: " + movementAcceleration.getMovementAcceleration());
        }
        else if (a instanceof DefaultAttribute)
        {
            System.err.println("characteristic for " + uuid + " is unknown");
        }
        else
        {
            System.out.println("unused characteristic " + a.getCharacteristic().getName());
        }
    }

    private static Date getLocalTime()
    {
        long time = new Date().getTime();
        while ((time % 1000) > 5)
        {
            time = new Date().getTime();
        }
        return new Date(time);
    }
}
