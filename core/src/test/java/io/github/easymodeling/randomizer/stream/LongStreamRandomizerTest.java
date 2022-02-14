package io.github.easymodeling.randomizer.stream;

import org.junit.jupiter.api.RepeatedTest;
import io.github.easymodeling.randomizer.number.LongRandomizer;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class LongStreamRandomizerTest {

    @RepeatedTest(100)
    void should_generate_long_stream() {
        LongStreamRandomizer randomizer = new LongStreamRandomizer(new LongRandomizer(1, 10), 2, 8);

        final List<Long> longs = randomizer.next().boxed().collect(Collectors.toList());

        assertThat(longs)
                .hasSizeBetween(2, 7)
                .allMatch(i -> i >= 1 && i < 10);
    }
}
