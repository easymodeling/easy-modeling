package xyz.v2my.easymodeling.randomizer.datetime;

import xyz.v2my.easymodeling.randomizer.GenericRandomizer;

import java.time.Instant;

public class InstantRandomizer extends GenericRandomizer<Instant> {

    @Override
    public Instant next() {
        final long milli = doubleBetween(0, 1000L * Integer.MAX_VALUE).longValue();
        return Instant.ofEpochMilli(milli);
    }
}
