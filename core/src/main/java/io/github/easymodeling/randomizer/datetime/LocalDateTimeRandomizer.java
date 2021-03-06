package io.github.easymodeling.randomizer.datetime;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class LocalDateTimeRandomizer extends AbstractDateTimeRandomizer<LocalDateTime> {

    public LocalDateTimeRandomizer(long min, long max) {
        super(min, max);
    }

    public LocalDateTimeRandomizer(Instant constant) {
        super(constant.atZone(ZoneId.systemDefault()).toLocalDateTime());
    }

    @Override
    public LocalDateTime random() {
        return instant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
