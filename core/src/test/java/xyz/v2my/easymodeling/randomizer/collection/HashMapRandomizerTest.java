package xyz.v2my.easymodeling.randomizer.collection;

import org.junit.jupiter.api.RepeatedTest;
import xyz.v2my.easymodeling.randomizer.RandomizerTest;
import xyz.v2my.easymodeling.randomizer.number.IntegerRandomizer;
import xyz.v2my.easymodeling.randomizer.string.StringRandomizer;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

class HashMapRandomizerTest extends RandomizerTest {

    @RepeatedTest(100)
    void should_generate_random_map() {
        final HashMapRandomizer<Integer, String> hashMapRandomizer = new HashMapRandomizer<>(new IntegerRandomizer(-2, 300), new StringRandomizer("some-string"), 3, 7);

        final HashMap<Integer, String> next = hashMapRandomizer.next();

        assertThat(next).hasSizeBetween(3, 6);
        assertThat(next.keySet()).allMatch(key -> key >= -2 && key <= 300);
        assertThat(next.values()).containsOnly("some-string");
    }
}
