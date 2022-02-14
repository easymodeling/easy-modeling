package io.github.easymodeling.randomizer.datetime;

import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;

public class LocalTimeRandomizer extends AbstractDateTimeRandomizer<LocalTime> {

    public LocalTimeRandomizer(long min, long max) {
        super(min, max);
    }

    public LocalTimeRandomizer(Instant constant) {
        super(constant.atZone(ZoneId.systemDefault()).toLocalTime());
    }

    @Override
    public LocalTime random() {
        return instant().atZone(ZoneId.systemDefault()).toLocalTime();
    }
}
