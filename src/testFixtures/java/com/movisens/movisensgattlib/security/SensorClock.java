package com.movisens.movisensgattlib.security;

/**
 * Time source for the {@link SpakeSensorEmulator} rate-limit lockout. Injectable so tests can
 * advance time deterministically instead of waiting out real lockout durations.
 */
public interface SensorClock
{
    long nowMillis();

    /** A clock that starts at 0 and only moves when {@link #advance(long)} is called. */
    final class Mutable implements SensorClock
    {
        private long now;

        @Override
        public long nowMillis()
        {
            return now;
        }

        public void advance(long millis)
        {
            now += millis;
        }
    }
}
