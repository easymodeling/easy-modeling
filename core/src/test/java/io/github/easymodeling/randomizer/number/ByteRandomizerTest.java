package io.github.easymodeling.randomizer.number;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import io.github.easymodeling.randomizer.RandomizerTest;

import static org.assertj.core.api.Assertions.assertThat;

class ByteRandomizerTest extends RandomizerTest {

    @RepeatedTest(100)
    void should_generate_byte_between_range() {
        ByteRandomizer randomizer = new ByteRandomizer(-2., 2.);

        final Byte next = randomizer.next();

        assertThat(next).isBetween((byte) -2, (byte) 2);
    }

    @Test
    void should_generate_constant_byte() {
        ByteRandomizer randomizer = new ByteRandomizer((byte) -2);

        final Byte next = randomizer.next();

        assertThat(next).isEqualTo((byte) -2);
    }
}
