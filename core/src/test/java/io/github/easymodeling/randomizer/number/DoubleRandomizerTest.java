package io.github.easymodeling.randomizer.number;

import io.github.easymodeling.randomizer.RandomizerTest;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DoubleRandomizerTest extends RandomizerTest {

    @RepeatedTest(100)
    void should_generate_double_between_range() {
        DoubleRandomizer randomizer = new DoubleRandomizer(-2., 2.);

        final Double next = randomizer.next();

        assertThat(next).isBetween(-2., 2.);
    }

    @Test
    void should_generate_constant_double() {
        DoubleRandomizer randomizer = new DoubleRandomizer(-2.);

        final Double next = randomizer.next();

        assertThat(next).isEqualTo(-2);
    }
}
