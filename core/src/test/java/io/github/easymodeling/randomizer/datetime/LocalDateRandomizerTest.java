package io.github.easymodeling.randomizer.datetime;

import io.github.easymodeling.randomizer.RandomizerTest;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;

class LocalDateRandomizerTest extends RandomizerTest {

    @RepeatedTest(100)
    void should_generate_random_local_date_within_range() {
        final Instant min = Instant.parse("2020-01-01T00:00:00Z");
        final Instant max = Instant.parse("2020-01-01T23:59:59Z");
        final LocalDateRandomizer localDateRandomizer = new LocalDateRandomizer(min.toEpochMilli(), max.toEpochMilli());

        final LocalDate next = localDateRandomizer.next();

        assertThat(next).isBetween(min.atZone(ZoneId.systemDefault()).toLocalDate(), max.atZone(ZoneId.systemDefault()).toLocalDate());
    }

    @Test
    void should_generate_constant_local_date() {
        final Instant constant = Instant.now();
        final LocalDateRandomizer localDateRandomizer = new LocalDateRandomizer(constant);

        final LocalDate next = localDateRandomizer.next();

        assertThat(next).isEqualTo(constant.atZone(ZoneId.systemDefault()).toLocalDate());
    }
}
