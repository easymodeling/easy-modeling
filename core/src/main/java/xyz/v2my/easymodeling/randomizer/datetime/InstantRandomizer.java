package xyz.v2my.easymodeling.randomizer.datetime;

import java.time.Instant;

public class InstantRandomizer extends AbstractDateTimeRandomizer<Instant> {

    public InstantRandomizer(long min, long max) {
        super(min, max);
    }

    public InstantRandomizer(Instant constant) {
        super(constant);
    }

    @Override
    public Instant random() {
        return instant();
    }
}
