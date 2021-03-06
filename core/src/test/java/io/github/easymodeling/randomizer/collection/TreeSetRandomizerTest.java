package io.github.easymodeling.randomizer.collection;

import io.github.easymodeling.randomizer.number.IntegerRandomizer;
import org.junit.jupiter.api.RepeatedTest;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class TreeSetRandomizerTest {

    @RepeatedTest(100)
    void should_generate_random_set_with_size_in_the_range() {
        TreeSetRandomizer<Integer> randomizer = new TreeSetRandomizer<>(new IntegerRandomizer(-200, 300), 33);

        final Set<Integer> set = randomizer.next();

        assertThat(set)
                .hasSizeLessThanOrEqualTo(33 - 1)
                .allMatch(i -> i >= -200 && i < 300);
    }
}
