package io.github.easymodeling.randomizer.datetime;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ZonedDateTimeRandomizer extends AbstractDateTimeRandomizer<ZonedDateTime> {

    public ZonedDateTimeRandomizer(long min, long max) {
        super(min, max);
    }

    public ZonedDateTimeRandomizer(Instant constant) {
        super(constant.atZone(ZoneId.systemDefault()));
    }

    @Override
    public ZonedDateTime random() {
        return instant().atZone(ZoneId.systemDefault());
    }
}
