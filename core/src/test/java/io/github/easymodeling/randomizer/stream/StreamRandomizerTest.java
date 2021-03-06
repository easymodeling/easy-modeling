package io.github.easymodeling.randomizer.stream;

import io.github.easymodeling.randomizer.RandomizerTest;
import io.github.easymodeling.randomizer.number.IntegerRandomizer;
import org.junit.jupiter.api.RepeatedTest;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class StreamRandomizerTest extends RandomizerTest {

    @RepeatedTest(100)
    void should_generate_stream_with_size_between_given_range() {
        StreamRandomizer<Integer> randomizer = new StreamRandomizer<>(new IntegerRandomizer(-5, 10), 1, 10);

        final List<Integer> generated = randomizer.next().collect(Collectors.toList());

        assertThat(generated)
                .hasSizeBetween(1, 9)
                .allMatch(i -> i >= -5 && i < 10);
    }
}
