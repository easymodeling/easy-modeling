package io.github.easymodeling.randomizer.collection;

import io.github.easymodeling.randomizer.number.IntegerRandomizer;
import io.github.easymodeling.randomizer.string.StringRandomizer;
import org.junit.jupiter.api.RepeatedTest;

import java.util.TreeMap;

import static org.assertj.core.api.Assertions.assertThat;

class TreeMapRandomizerTest {

    @RepeatedTest(100)
    void should_generate_random_map() {
        final TreeMapRandomizer<Integer, String> treeMapRandomizer = new TreeMapRandomizer<>(new IntegerRandomizer(-2, 300), new StringRandomizer("some-string"), 7);

        final TreeMap<Integer, String> next = treeMapRandomizer.next();

        assertThat(next).hasSizeLessThanOrEqualTo(7 - 1);
        assertThat(next.keySet()).allMatch(key -> key >= -2 && key <= 300);
        assertThat(next.values()).containsOnly("some-string");
    }
}
