package io.github.easymodeling.randomizer.collection;

import org.junit.jupiter.api.RepeatedTest;
import io.github.easymodeling.randomizer.number.IntegerRandomizer;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class TreeSetRandomizerTest {

    @RepeatedTest(100)
    void should_generate_random_set_with_size_in_the_range() {
        TreeSetRandomizer<Integer> randomizer = new TreeSetRandomizer<>(new IntegerRandomizer(-200, 300), 20, 33);

        final Set<Integer> set = randomizer.next();

        assertThat(set)
                .hasSizeBetween(20, 33 - 1)
                .allMatch(i -> i >= -200 && i < 300);
    }
}
