package xyz.v2my.easymodeling.randomizer.number;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import xyz.v2my.easymodeling.randomizer.RandomizerTest;

import static org.assertj.core.api.Assertions.assertThat;

class IntegerRandomizerTest extends RandomizerTest {

    @RepeatedTest(100)
    void should_generate_integer_between_range() {
        IntegerRandomizer randomizer = new IntegerRandomizer(-2., 2.);

        final Integer next = randomizer.next();

        assertThat(next).isBetween(-2, 2);
    }

    @Test
    void should_generate_constant_integer() {
        IntegerRandomizer randomizer = new IntegerRandomizer(-2);

        final Integer next = randomizer.next();

        assertThat(next).isEqualTo(-2);
    }
}
