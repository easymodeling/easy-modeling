package xyz.v2my.easymodeling.randomizer.datetime;

import xyz.v2my.easymodeling.randomizer.GenericRandomizer;

import java.time.Instant;

public abstract class AbstractDateTimeRandomizer<T> extends GenericRandomizer<T> {

    private long min;

    private long max;

    public AbstractDateTimeRandomizer(long min, long max) {
        this.min = min;
        this.max = max;
    }

    public AbstractDateTimeRandomizer(T constant) {
        super(constant);
    }

    public Instant instant() {
        final long milli = doubleBetween(min, max).longValue();
        return Instant.ofEpochMilli(milli);
    }
}
