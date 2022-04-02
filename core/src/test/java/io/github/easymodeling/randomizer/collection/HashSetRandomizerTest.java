package io.github.easymodeling.randomizer.collection;

import io.github.easymodeling.randomizer.RandomizerTest;
import io.github.easymodeling.randomizer.number.IntegerRandomizer;
import org.junit.jupiter.api.RepeatedTest;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class HashSetRandomizerTest extends RandomizerTest {

    @RepeatedTest(100)
    void should_generate_random_set_with_size_in_the_range() {
        HashSetRandomizer<Integer> randomizer = new HashSetRandomizer<>(new IntegerRandomizer(-2, 300), 7);

        final Set<Integer> set = randomizer.next();

        assertThat(set)
                .hasSizeLessThanOrEqualTo(7 - 1)
                .allMatch(i -> i >= -2 && i < 300);
    }
}
