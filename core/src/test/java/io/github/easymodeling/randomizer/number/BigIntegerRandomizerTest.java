package io.github.easymodeling.randomizer.number;

import io.github.easymodeling.randomizer.RandomizerTest;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;

class BigIntegerRandomizerTest extends RandomizerTest {

    @RepeatedTest(100)
    void should_generate_byte_between_range() {
        BigIntegerRandomizer randomizer = new BigIntegerRandomizer(-3., 1.);

        final BigInteger next = randomizer.next();

        assertThat(next).isBetween(BigInteger.valueOf(-3L), BigInteger.valueOf(1L));
    }

    @Test
    void should_generate_constant_byte() {
        BigIntegerRandomizer randomizer = new BigIntegerRandomizer(BigInteger.valueOf(-2L));

        final BigInteger next = randomizer.next();

        assertThat(next).isEqualTo(BigInteger.valueOf(-2L));
    }
}
