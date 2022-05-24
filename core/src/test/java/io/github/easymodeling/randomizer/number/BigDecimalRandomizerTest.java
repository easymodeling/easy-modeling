package io.github.easymodeling.randomizer.number;

import io.github.easymodeling.randomizer.RandomizerTest;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class BigDecimalRandomizerTest extends RandomizerTest {

    @RepeatedTest(100)
    void should_generate_byte_between_range() {
        BigDecimalRandomizer randomizer = new BigDecimalRandomizer(-3.6, 1.1);

        final BigDecimal next = randomizer.next();

        assertThat(next).isBetween(BigDecimal.valueOf(-3.6), BigDecimal.valueOf(1.1));
    }

    @Test
    void should_generate_constant_byte() {
        BigDecimalRandomizer randomizer = new BigDecimalRandomizer(BigDecimal.valueOf(-2.1));

        final BigDecimal next = randomizer.next();

        assertThat(next).isEqualTo(BigDecimal.valueOf(-2.1));
    }
}
