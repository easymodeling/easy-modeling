package xyz.v2my.easymodeling.randomizer.array;

import org.assertj.core.data.Index;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import xyz.v2my.easymodeling.randomizer.GenericRandomizerDecorator;
import xyz.v2my.easymodeling.randomizer.Randomizer;
import xyz.v2my.easymodeling.randomizer.number.ByteRandomizer;
import xyz.v2my.easymodeling.randomizer.number.DoubleRandomizer;
import xyz.v2my.easymodeling.randomizer.number.FloatRandomizer;
import xyz.v2my.easymodeling.randomizer.number.IntegerRandomizer;
import xyz.v2my.easymodeling.randomizer.number.LongRandomizer;
import xyz.v2my.easymodeling.randomizer.number.ShortRandomizer;
import xyz.v2my.easymodeling.randomizer.primitive.BooleanRandomizer;
import xyz.v2my.easymodeling.randomizer.primitive.CharRandomizer;

import java.lang.reflect.Array;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class PrimitiveArrayRandomizerTest {

    @BeforeEach
    void setUp() {
        GenericRandomizerDecorator.decorateSeed(0);
    }

    @Test
    void should_generate_1_dimension_array() {
        final IntegerRandomizer integerRandomizer = new IntegerRandomizer(1, 2);

        final int[] next = (int[]) new PrimitiveArrayRandomizer(integerRandomizer, 1, 3, 6).next();

        assertThat(next).hasSize(5).contains(1, 1, 1, 1, 1);
    }

    @Test
    void should_generate_2_dimension_array() {
        final IntegerRandomizer integerRandomizer = new IntegerRandomizer(1, 2);

        final int[][] next = (int[][]) new PrimitiveArrayRandomizer(integerRandomizer, 2, 3, 6).next();

        assertThat(next).hasDimensions(5, 3)
                .contains(new int[]{1, 1, 1}, Index.atIndex(0))
                .contains(new int[]{1, 1, 1}, Index.atIndex(1))
                .contains(new int[]{1, 1, 1}, Index.atIndex(2))
                .contains(new int[]{1, 1, 1}, Index.atIndex(3))
                .contains(new int[]{1, 1, 1}, Index.atIndex(4));
    }

    @ParameterizedTest
    @MethodSource("provideNestedRandomizer")
    void should_generate_primitive_arrays(Randomizer<?> primitiveRandomizer) {
        Object next = new PrimitiveArrayRandomizer(primitiveRandomizer, 2, 3, 6).next();

        assertThat(Array.getLength(next)).isEqualTo(5);
    }

    private static Stream<Arguments> provideNestedRandomizer() {
        return Stream.of(
                Arguments.of(new IntegerRandomizer(1)),
                Arguments.of(new LongRandomizer(1L)),
                Arguments.of(new BooleanRandomizer()),
                Arguments.of(new ByteRandomizer(1, 2)),
                Arguments.of(new CharRandomizer()),
                Arguments.of(new FloatRandomizer(1, 2)),
                Arguments.of(new DoubleRandomizer(1, 2)),
                Arguments.of(new ShortRandomizer(1, 2))
        );
    }
}
