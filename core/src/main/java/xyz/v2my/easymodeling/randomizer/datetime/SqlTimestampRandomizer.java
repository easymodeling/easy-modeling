package xyz.v2my.easymodeling.randomizer.datetime;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;

public class SqlTimestampRandomizer extends AbstractDateTimeRandomizer<Timestamp> {

    public SqlTimestampRandomizer(long min, long max) {
        super(min, max);
    }

    public SqlTimestampRandomizer(Instant constant) {
        super(Timestamp.valueOf(constant.atZone(ZoneId.systemDefault()).toLocalDateTime()));
    }

    @Override
    public Timestamp random() {
        return Timestamp.valueOf(instant().atZone(ZoneId.systemDefault()).toLocalDateTime());
    }
}
