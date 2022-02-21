package io.github.easymodeling.randomizer.stream;

import io.github.easymodeling.randomizer.RandomizerTest;
import io.github.easymodeling.randomizer.number.IntegerRandomizer;
import org.junit.jupiter.api.RepeatedTest;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class IntStreamRandomizerTest extends RandomizerTest {

    @RepeatedTest(100)
    void should_generate_int_stream() {
        IntStreamRandomizer randomizer = new IntStreamRandomizer(new IntegerRandomizer(-5, 10), 100, 110);

        final List<Integer> ints = randomizer.next().boxed().collect(Collectors.toList());

        assertThat(ints)
                .hasSizeBetween(100, 109)
                .allMatch(i -> i >= -5 && i < 10);
    }
}
