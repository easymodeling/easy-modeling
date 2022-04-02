package io.github.easymodeling.randomizer.collection;

import io.github.easymodeling.randomizer.RandomizerTest;
import io.github.easymodeling.randomizer.number.IntegerRandomizer;
import io.github.easymodeling.randomizer.string.StringRandomizer;
import org.junit.jupiter.api.RepeatedTest;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

class HashMapRandomizerTest extends RandomizerTest {

    @RepeatedTest(100)
    void should_generate_random_map() {
        final HashMapRandomizer<Integer, String> hashMapRandomizer = new HashMapRandomizer<>(new IntegerRandomizer(-2, 300), new StringRandomizer("some-string"), 7);

        final HashMap<Integer, String> next = hashMapRandomizer.next();

        assertThat(next).hasSizeLessThanOrEqualTo(7 - 1);
        assertThat(next.keySet()).allMatch(key -> key >= -2 && key <= 300);
        assertThat(next.values()).containsOnly("some-string");
    }
}
