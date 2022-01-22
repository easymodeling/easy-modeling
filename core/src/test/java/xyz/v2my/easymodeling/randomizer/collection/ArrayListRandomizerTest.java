package xyz.v2my.easymodeling.randomizer.collection;

import org.junit.jupiter.api.RepeatedTest;
import xyz.v2my.easymodeling.randomizer.RandomizerTest;
import xyz.v2my.easymodeling.randomizer.number.IntegerRandomizer;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

class ArrayListRandomizerTest extends RandomizerTest {

    @RepeatedTest(100)
    void should_generate_random_array_list_with_size_in_the_range() {
        ArrayListRandomizer<Integer> randomizer = new ArrayListRandomizer<>(new IntegerRandomizer(-2, 3), 3, 7);

        final ArrayList<Integer> list = randomizer.next();

        assertThat(list)
                .hasSizeBetween(3, 6)
                .allMatch(i -> i >= -2 && i < 3);
    }
}
