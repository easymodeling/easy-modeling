package io.github.easymodeling.randomizer.datetime;

import io.github.easymodeling.randomizer.GenericRandomizer;

import java.time.Instant;

public abstract class AbstractDateTimeRandomizer<T> extends GenericRandomizer<T> {

    private long min;

    private long max;

    protected AbstractDateTimeRandomizer(long min, long max) {
        this.min = min;
        this.max = max;
    }

    protected AbstractDateTimeRandomizer(T constant) {
        super(constant);
    }

    public Instant instant() {
        final long milli = doubleBetween(min, max).longValue();
        return Instant.ofEpochMilli(milli);
    }
}
