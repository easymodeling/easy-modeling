package xyz.v2my.easymodeling.randomizer.collection;

import org.junit.jupiter.api.Test;
import xyz.v2my.easymodeling.randomizer.RandomizerTest;
import xyz.v2my.easymodeling.randomizer.array.ArrayRandomizer;
import xyz.v2my.easymodeling.randomizer.number.IntegerRandomizer;

import java.lang.reflect.Array;

import static org.assertj.core.api.Assertions.assertThat;

class ArrayRandomizerTest extends RandomizerTest {

    @Test
    void should_populate_array() {
        final ArrayRandomizer<Integer> arrayRandomizer = new ArrayRandomizer<>(new IntegerRandomizer(1, 2), 3, 3);

        assertThat(arrayRandomizer.next()).hasSize(3);
    }

    @Test
    void should_populate_matrix() {
        final ArrayRandomizer<Integer> arrayRandomizer = new ArrayRandomizer<>(new IntegerRandomizer(1, 2), 3, 3);
        final ArrayRandomizer<Integer[]> matrixArrayRandomizer = new ArrayRandomizer<>(arrayRandomizer, 3, 3);

        assertThat(matrixArrayRandomizer.next()).hasDimensions(3, 3);
    }

    @Test
    void should_populate_cube() {
        final ArrayRandomizer<Integer> arrayRandomizer = new ArrayRandomizer<>(new IntegerRandomizer(1, 2), 4, 4);
        final ArrayRandomizer<Integer[]> matrixRandomizer = new ArrayRandomizer<>(arrayRandomizer, 4, 4);
        final ArrayRandomizer<Integer[][]> cubeArrayRandomizer = new ArrayRandomizer<>(matrixRandomizer, 4, 4);

        assertThat(cubeArrayRandomizer.next()).hasSameDimensionsAs(Array.newInstance(Integer.class, 4, 4, 4));
    }
}
