package io.github.easymodeling.randomizer.number;

import io.github.easymodeling.randomizer.RandomizerTest;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ShortRandomizerTest extends RandomizerTest {

    @RepeatedTest(100)
    void should_generate_short_between_range() {
        ShortRandomizer randomizer = new ShortRandomizer(-2., 2.);

        final Short next = randomizer.next();

        assertThat(next).isBetween((short) -2, (short) 2);
    }

    @Test
    void should_generate_constant_short() {
        ShortRandomizer randomizer = new ShortRandomizer((short) 2);

        final Short next = randomizer.next();

        assertThat(next).isEqualTo((short) 2);
    }
}
