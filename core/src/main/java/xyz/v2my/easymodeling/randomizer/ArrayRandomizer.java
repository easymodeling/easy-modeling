package xyz.v2my.easymodeling.randomizer;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.stream.IntStream;

public class ArrayRandomizer<E> extends GenericRandomizer<Object> {

    private final Randomizer<E> elementRandomizer;

    private final Class<E> elementClass;

    private final int dimension;

    public ArrayRandomizer(Randomizer<E> elementRandomizer, Class<E> elementClass, int dimension) {
        this.elementRandomizer = elementRandomizer;
        this.elementClass = elementClass;
        this.dimension = dimension;
    }

    @Override
    public Object next() {
        final int[] dimensions = IntStream.range(0, dimension).map(i -> doubleBetween(1, 10).intValue()).toArray();
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
