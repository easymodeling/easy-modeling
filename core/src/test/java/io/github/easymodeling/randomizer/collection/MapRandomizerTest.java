package io.github.easymodeling.randomizer.collection;

import io.github.easymodeling.randomizer.RandomizerTest;
import io.github.easymodeling.randomizer.number.IntegerRandomizer;
import io.github.easymodeling.randomizer.string.StringRandomizer;
import org.junit.jupiter.api.RepeatedTest;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class MapRandomizerTest extends RandomizerTest {

    @RepeatedTest(100)
    void should_generate_random_map() {
        final MapRandomizer<Integer, String> mapRandomizer = new MapRandomizer<>(new IntegerRandomizer(-2, 300), new StringRandomizer("some-string"), 3, 7);

        final Map<Integer, String> next = mapRandomizer.next();

        assertThat(next).hasSizeBetween(3, 6);
        assertThat(next.keySet()).allMatch(key -> key >= -2 && key <= 300);
        assertThat(next.values()).containsOnly("some-string");
    }
}
