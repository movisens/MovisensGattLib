package com.movisens.movisensgattlib.characteristics;

import com.movisens.smartgattlib.GattByteBuffer;
import com.movisens.smartgattlib.characteristics.definition.AbstractReadOnlyCharacteristic;

/**
 * Created by Robert Zetzsche on 28.09.2016.
 */

public class BodyPosition extends AbstractReadOnlyCharacteristic<BodyPosition.Position> {
    public BodyPosition(byte[] bytes) {
        super(bytes);
    }

    @Override
    protected Position getValueForBytes(byte[] bytes) {
        return Position.getSensorPositionByValue(GattByteBuffer.wrap(bytes).getUint8());
    }

    public enum Position {
        UNKNOWN(0), LYING_SUPINE(1), LYING_LEFT(2), LYING_PRONE(3), LYING_RIGHT(4), UPRIGHT(5), SITTING_LYING(6), STANDING(7), NOT_WORN(99);

        public final int value;

        Position(int value) {
            this.value = value;
        }

        public static BodyPosition.Position getSensorPositionByValue(int positionValue) {
            switch (positionValue) {
                case 0:
                    return Position.UNKNOWN;
                case 1:
                    return Position.LYING_SUPINE;
                case 2:
                    return Position.LYING_LEFT;
                case 3:
                    return Position.LYING_PRONE;
                case 4:
                    return Position.LYING_RIGHT;
                case 5:
                    return Position.UPRIGHT;
                case 6:
                    return Position.SITTING_LYING;
                case 7:
                    return Position.STANDING;
                case 99:
                    return Position.NOT_WORN;
                default:
                    return Position.UNKNOWN;
            }
        }

    }
}
