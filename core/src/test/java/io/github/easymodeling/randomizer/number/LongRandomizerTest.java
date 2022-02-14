package io.github.easymodeling.randomizer.number;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import io.github.easymodeling.randomizer.RandomizerTest;

import static org.assertj.core.api.Assertions.assertThat;

class LongRandomizerTest extends RandomizerTest {

    @RepeatedTest(100)
    void should_generate_long_between_range() {
        LongRandomizer randomizer = new LongRandomizer(-2., 2.);

        final Long next = randomizer.next();

        assertThat(next).isBetween((long) -2, (long) 2);
    }

    @Test
    void should_generate_constant_long() {
        LongRandomizer randomizer = new LongRandomizer(-2L);

        final Long next = randomizer.next();

        assertThat(next).isEqualTo(-2L);
    }
}
