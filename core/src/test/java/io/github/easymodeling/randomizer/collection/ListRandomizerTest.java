package io.github.easymodeling.randomizer.collection;

import io.github.easymodeling.randomizer.RandomizerTest;
import io.github.easymodeling.randomizer.number.IntegerRandomizer;
import org.junit.jupiter.api.RepeatedTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ListRandomizerTest extends RandomizerTest {

    @RepeatedTest(100)
    void should_generate_random_list_with_size_in_the_range() {
        ListRandomizer<Integer> randomizer = new ListRandomizer<>(new IntegerRandomizer(-2, 3), 3, 7);

        final List<Integer> list = randomizer.next();

        assertThat(list)
                .hasSizeBetween(3, 6)
                .allMatch(i -> i >= -2 && i < 3);
    }
}
