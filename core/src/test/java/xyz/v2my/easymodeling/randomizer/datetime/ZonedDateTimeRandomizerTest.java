package xyz.v2my.easymodeling.randomizer.datetime;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import xyz.v2my.easymodeling.randomizer.RandomizerTest;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class ZonedDateTimeRandomizerTest extends RandomizerTest {

    @RepeatedTest(100)
    void should_generate_random_zoned_date_time_within_range() {
        final Instant min = Instant.parse("2020-01-01T10:59:35Z");
        final Instant max = Instant.parse("2020-01-01T11:01:01Z");
        final ZonedDateTimeRandomizer zonedDateTimeRandomizer = new ZonedDateTimeRandomizer(min.toEpochMilli(), max.toEpochMilli());

        final ZonedDateTime next = zonedDateTimeRandomizer.next();

        assertThat(next).isBetween(ZonedDateTime.parse("2020-01-01T10:59:35Z"), ZonedDateTime.parse("2020-01-01T11:01:01Z"));
    }

    @Test
    void should_generate_constant_zoned_date_time() {
        final Instant constant = Instant.now();
        final ZonedDateTimeRandomizer zonedDateTimeRandomizer = new ZonedDateTimeRandomizer(constant);

        final ZonedDateTime next = zonedDateTimeRandomizer.next();

        assertThat(next).isEqualTo(constant.atZone(ZoneId.systemDefault()));
    }
}
