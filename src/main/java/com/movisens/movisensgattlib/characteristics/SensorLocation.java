package com.movisens.movisensgattlib.characteristics;

import com.movisens.smartgattlib.GattByteBuffer;

public class SensorLocation {
    private byte[] bytes;
    private Position value;

    public SensorLocation(Position position) {
        this.value = position;
        this.bytes = GattByteBuffer.allocate(4).putUint16(position.value).array();
    }

    public SensorLocation(byte[] bytes) {
        this.bytes = bytes;
        this.value = Position.getSensorPositionByValue(GattByteBuffer.wrap(bytes).getUint16());
    }

    public byte[] getBytes() {
        return bytes;
    }

    public Position getValue() {
        return value;
    }

    public enum Position {
        RIGHT_SIDE_HIP(1), CHEST(2), RIGHT_WRIST(3), LEFT_WRIST(4), LEFT_ANKLE(5), RIGHT_ANKLE(6), RIGHT_THIGH(
                7), LEFT_THIGH(8), RIGHT_UPPER_ARM(9), LEFT_UPPER_ARM(10), LEFT_SIDE_HIP(11);

        public final int value;

        Position(int value) {
            this.value = value;
        }

        public static SensorLocation.Position getSensorPositionByValue(int positionValue) {
            switch (positionValue) {
                case 1:
                    return Position.RIGHT_SIDE_HIP;
                case 2:
                    return Position.CHEST;
                case 3:
                    return Position.RIGHT_WRIST;
                case 4:
                    return Position.LEFT_WRIST;
                case 5:
                    return Position.LEFT_ANKLE;
                case 6:
                    return Position.RIGHT_ANKLE;
                case 7:
                    return Position.RIGHT_THIGH;
                case 8:
                    return Position.LEFT_THIGH;
                case 9:
                    return Position.RIGHT_UPPER_ARM;
                case 10:
                    return Position.LEFT_UPPER_ARM;
                case 11:
                    return Position.LEFT_SIDE_HIP;
                default:
                    return Position.RIGHT_SIDE_HIP;
            }
        }

    }

}
