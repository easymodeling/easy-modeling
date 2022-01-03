package xyz.v2my.easymodeling.randomizer;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class ArrayRandomizer extends GenericRandomizer<Object> {

    private final Randomizer<?> elementRandomizer;

    private final Class<?> elementClass;

    private final int dimension;

    private final int min;

    private final int max;

    public ArrayRandomizer(Randomizer<?> elementRandomizer, Class<?> elementClass, int dimension, int min, int max) {
        this.elementRandomizer = elementRandomizer;
        this.elementClass = elementClass;
        this.dimension = dimension;
        this.min = min;
        this.max = max;
    }

    @Override
    public Object random() {
        final int[] dimensions = IntStream.range(0, dimension).map(i -> doubleBetween(min, max).intValue()).toArray();
        return generateArray(dimensions);
    }


    private Object generateArray(int... dimensions) {
        final Object array = Array.newInstance(elementClass, dimensions);
        final int[] shiftedDimensions = Arrays.copyOfRange(dimensions, 1, dimensions.length);
        final Supplier<Object> supplier = dimensions.length == 1 ? elementRandomizer::next : () -> generateArray(shiftedDimensions);
        for (int i = 0; i < dimensions[0]; i++) {
            Array.set(array, i, supplier.get());
        }
        return array;
    }
}
