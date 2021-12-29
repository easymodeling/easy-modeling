package xyz.v2my.easymodeling.randomizer.datetime;

import xyz.v2my.easymodeling.randomizer.GenericRandomizer;

import java.time.Instant;

public class InstantRandomizer extends GenericRandomizer<Instant> {

    protected final long min;

    protected final long max;

    public InstantRandomizer(long min, long max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public Instant next() {
        final long milli = doubleBetween(min, max).longValue();
        return Instant.ofEpochMilli(milli);
    }
}
