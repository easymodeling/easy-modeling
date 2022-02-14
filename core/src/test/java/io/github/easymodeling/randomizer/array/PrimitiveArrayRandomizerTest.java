package io.github.easymodeling.randomizer.array;

import org.assertj.core.data.Index;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import io.github.easymodeling.randomizer.Randomizer;
import io.github.easymodeling.randomizer.RandomizerTest;
import io.github.easymodeling.randomizer.datetime.InstantRandomizer;
import io.github.easymodeling.randomizer.number.ByteRandomizer;
import io.github.easymodeling.randomizer.number.DoubleRandomizer;
import io.github.easymodeling.randomizer.number.FloatRandomizer;
import io.github.easymodeling.randomizer.number.IntegerRandomizer;
import io.github.easymodeling.randomizer.number.LongRandomizer;
import io.github.easymodeling.randomizer.number.ShortRandomizer;
import io.github.easymodeling.randomizer.primitive.BooleanRandomizer;
import io.github.easymodeling.randomizer.primitive.CharRandomizer;

import java.lang.reflect.Array;
import java.time.Instant;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class PrimitiveArrayRandomizerTest extends RandomizerTest {

    @BeforeEach
    void setUp() {
        baseSetUp();
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

    @Test
    void should_throw_when_field_type_is_not_primitive() {
        final InstantRandomizer instantRandomizer = new InstantRandomizer(Instant.now());

        final Throwable throwable = catchThrowable(() -> new PrimitiveArrayRandomizer(instantRandomizer, 1, 3, 6).next());

        assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
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
