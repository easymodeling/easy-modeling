package xyz.v2my.easymodeling.randomizer.collection;

import org.junit.jupiter.api.RepeatedTest;
import xyz.v2my.easymodeling.randomizer.RandomizerTest;
import xyz.v2my.easymodeling.randomizer.number.IntegerRandomizer;

import java.util.LinkedList;

import static org.assertj.core.api.Assertions.assertThat;

class LinkedListRandomizerTest extends RandomizerTest {

    @RepeatedTest(100)
    void should_generate_random_linked_list_with_size_in_the_range() {
        LinkedListRandomizer<Integer> randomizer = new LinkedListRandomizer<>(new IntegerRandomizer(-2, 3), 3, 7);

        final LinkedList<Integer> list = randomizer.next();

        assertThat(list)
                .hasSizeBetween(3, 6)
                .allMatch(i -> i >= -2 && i < 3);
    }
}
