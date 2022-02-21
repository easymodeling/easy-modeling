package io.github.easymodeling.randomizer.datetime;

import io.github.easymodeling.randomizer.RandomizerTest;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class InstantRandomizerTest extends RandomizerTest {

    @RepeatedTest(100)
    void should_generate_random_instant_within_range() {
        final Instant min = Instant.parse("2020-01-01T00:00:00Z");
        final Instant max = Instant.parse("2020-01-01T23:59:59Z");
        final InstantRandomizer instantRandomizer = new InstantRandomizer(min.toEpochMilli(), max.toEpochMilli());

        final Instant next = instantRandomizer.next();

        assertThat(next).isBetween(min, max);
    }

    @Test
    void should_generate_constant_instant() {
        final Instant constant = Instant.now();
        final InstantRandomizer instantRandomizer = new InstantRandomizer(constant);

        final Instant next = instantRandomizer.next();

        assertThat(next).isEqualTo(constant);
    }
}
