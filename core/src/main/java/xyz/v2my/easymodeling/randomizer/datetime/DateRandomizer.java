package xyz.v2my.easymodeling.randomizer.datetime;

import java.time.Instant;
import java.util.Date;

public class DateRandomizer extends AbstractDateTimeRandomizer<Date> {

    public DateRandomizer(long min, long max) {
        super(min, max);
    }

    public DateRandomizer(Instant constant) {
        super(Date.from(constant));
    }

    @Override
    public Date random() {
        return Date.from(instant());
    }
}
