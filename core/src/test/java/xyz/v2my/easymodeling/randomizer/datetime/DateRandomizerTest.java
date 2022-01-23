package xyz.v2my.easymodeling.randomizer.datetime;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import xyz.v2my.easymodeling.randomizer.RandomizerTest;

import java.time.Instant;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class DateRandomizerTest extends RandomizerTest {

    @RepeatedTest(100)
    void should_generate_random_date_within_range() {
        final Instant min = Instant.parse("2020-01-01T10:59:35Z");
        final Instant max = Instant.parse("2020-01-01T11:01:01Z");
        final DateRandomizer zonedDateTimeRandomizer = new DateRandomizer(min.toEpochMilli(), max.toEpochMilli());

        final Date next = zonedDateTimeRandomizer.next();

        assertThat(next).isBetween(Date.from(min), Date.from(max));
    }

    @Test
    void should_generate_constant_date() {
        final Instant constant = Instant.now();
        final DateRandomizer zonedDateTimeRandomizer = new DateRandomizer(constant);

        final Date next = zonedDateTimeRandomizer.next();

        assertThat(next).isEqualTo(Date.from(constant));
    }
}
