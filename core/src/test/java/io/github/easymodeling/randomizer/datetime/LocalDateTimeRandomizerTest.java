package io.github.easymodeling.randomizer.datetime;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import io.github.easymodeling.randomizer.RandomizerTest;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;

class LocalDateTimeRandomizerTest extends RandomizerTest {

    @RepeatedTest(100)
    void should_generate_random_local_time_within_range() {
        final Instant min = Instant.parse("2020-01-01T10:59:35Z");
        final Instant max = Instant.parse("2020-01-01T11:01:01Z");
        final LocalDateTimeRandomizer localDateTimeRandomizer = new LocalDateTimeRandomizer(min.toEpochMilli(), max.toEpochMilli());

        final LocalDateTime next = localDateTimeRandomizer.next();

        assertThat(next).isBetween(min.atZone(ZoneId.systemDefault()).toLocalDateTime(), max.atZone(ZoneId.systemDefault()).toLocalDateTime());
    }

    @Test
    void should_generate_constant_local_time() {
        final Instant constant = Instant.now();
        final LocalDateTimeRandomizer localDateTimeRandomizer = new LocalDateTimeRandomizer(constant);

        final LocalDateTime next = localDateTimeRandomizer.next();

        assertThat(next).isEqualTo(constant.atZone(ZoneId.systemDefault()).toLocalDateTime());
    }
}
