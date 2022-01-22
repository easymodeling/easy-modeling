package xyz.v2my.easymodeling.randomizer.datetime;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import xyz.v2my.easymodeling.randomizer.RandomizerTest;

import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;

class LocalTimeRandomizerTest extends RandomizerTest {

    @RepeatedTest(100)
    void should_generate_random_local_time_within_range() {
        final Instant min = Instant.parse("2020-01-01T10:59:35Z");
        final Instant max = Instant.parse("2020-01-01T11:01:01Z");
        final LocalTimeRandomizer localTimeRandomizer = new LocalTimeRandomizer(min.toEpochMilli(), max.toEpochMilli());

        final LocalTime next = localTimeRandomizer.next();

        assertThat(next).isBetween(LocalTime.parse("10:59:35"), LocalTime.parse("11:01:01"));
    }

    @Test
    void should_generate_constant_local_time() {
        final Instant constant = Instant.now();
        final LocalTimeRandomizer localTimeRandomizer = new LocalTimeRandomizer(constant);

        final LocalTime next = localTimeRandomizer.next();

        assertThat(next).isEqualTo(constant.atZone(ZoneId.of("UTC")).toLocalTime());
    }
}
