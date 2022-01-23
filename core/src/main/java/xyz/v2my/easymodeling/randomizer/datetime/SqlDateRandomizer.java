package xyz.v2my.easymodeling.randomizer.datetime;

import java.sql.Date;
import java.time.Instant;
import java.time.ZoneId;

public class SqlDateRandomizer extends AbstractDateTimeRandomizer<Date> {

    public SqlDateRandomizer(long min, long max) {
        super(min, max);
    }

    public SqlDateRandomizer(Instant constant) {
        super(Date.valueOf(constant.atZone(ZoneId.systemDefault()).toLocalDate()));
    }

    @Override
    public Date random() {
        return Date.valueOf(instant().atZone(ZoneId.systemDefault()).toLocalDate());
    }
}
