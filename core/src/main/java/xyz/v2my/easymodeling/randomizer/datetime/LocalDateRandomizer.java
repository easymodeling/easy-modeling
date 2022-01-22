package xyz.v2my.easymodeling.randomizer.datetime;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class LocalDateRandomizer extends AbstractDateTimeRandomizer<LocalDate> {

    public LocalDateRandomizer(long min, long max) {
        super(min, max);
    }

    public LocalDateRandomizer(Instant constant) {
        super(constant.atZone(ZoneId.of("UTC")).toLocalDate());
    }

    @Override
    public LocalDate random() {
        return instant().atZone(ZoneId.of("UTC")).toLocalDate();
    }
}
