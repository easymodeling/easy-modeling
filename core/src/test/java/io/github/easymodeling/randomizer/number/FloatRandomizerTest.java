package io.github.easymodeling.randomizer.number;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import io.github.easymodeling.randomizer.RandomizerTest;

import static org.assertj.core.api.Assertions.assertThat;

class FloatRandomizerTest extends RandomizerTest {

    @RepeatedTest(100)
    void should_generate_float_between_range() {
        FloatRandomizer randomizer = new FloatRandomizer(-2., 2.);

        final Float next = randomizer.next();

        assertThat(next).isBetween((float) -2, (float) 2);
    }

    @Test
    void should_generate_constant_float() {
        FloatRandomizer randomizer = new FloatRandomizer((float) -2);

        final Float next = randomizer.next();

        assertThat(next).isEqualTo((float) -2);
    }
}
