package xyz.v2my.easymodeling.randomizer.stream;

import org.junit.jupiter.api.RepeatedTest;
import xyz.v2my.easymodeling.randomizer.RandomizerTest;
import xyz.v2my.easymodeling.randomizer.number.IntegerRandomizer;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class StreamRandomizerTest extends RandomizerTest {

    @RepeatedTest(100)
    void should_generate_stream_with_size_between_given_range() {
        StreamRandomizer<Integer> randomizer = new StreamRandomizer<>(new IntegerRandomizer(-5, 10), 1, 10);

        final List<Integer> generated = randomizer.next().collect(Collectors.toList());

        assertThat(generated).hasSizeBetween(1, 9);
        assertThat(generated).allMatch(i -> i >= -5 && i < 10);
    }
}
