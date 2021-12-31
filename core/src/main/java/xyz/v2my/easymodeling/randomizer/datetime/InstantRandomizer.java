package xyz.v2my.easymodeling.randomizer.datetime;

import xyz.v2my.easymodeling.randomizer.GenericRandomizer;

import java.time.Instant;

public class InstantRandomizer extends GenericRandomizer<Instant> {

    protected long min;

    protected long max;

    public InstantRandomizer(long min, long max) {
        this.min = min;
        this.max = max;
    }

    public InstantRandomizer(Instant constant) {
        super(constant);
    }

    @Override
    public Instant random() {
        final long milli = doubleBetween(min, max).longValue();
        return Instant.ofEpochMilli(milli);
    }
}
