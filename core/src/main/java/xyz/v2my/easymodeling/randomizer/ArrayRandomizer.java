package xyz.v2my.easymodeling.randomizer;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.stream.IntStream;

public class ArrayRandomizer<E> extends GenericRandomizer<Object> {

    private final Randomizer<E> elementRandomizer;

    private final Class<E> elementClass;

    private final int dimension;

    private final int min;

    private final int max;

    public ArrayRandomizer(Randomizer<E> elementRandomizer, Class<E> elementClass, int dimension, int min, int max) {
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
        for (int i = 0; i < dimensions[0]; i++) {
            final Object next = dimensions.length == 1 ?
                    elementRandomizer.next() :
                    generateArray(Arrays.copyOfRange(dimensions, 1, dimensions.length));
            Array.set(array, i, next);
        }
        return array;
    }
}
