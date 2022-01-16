package xyz.v2my.easymodeling.randomizer.array;

import xyz.v2my.easymodeling.randomizer.GenericRandomizer;
import xyz.v2my.easymodeling.randomizer.Randomizer;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class PrimitiveArrayRandomizer extends GenericRandomizer<Object> {

    private final Randomizer<?> elementRandomizer;

    private final int dimension;

    private final int min;

    private final int max;

    public PrimitiveArrayRandomizer(Randomizer<?> elementRandomizer, int dimension, int min, int max) {
        this.elementRandomizer = elementRandomizer;
        this.dimension = dimension;
        this.min = min;
        this.max = max;
    }

    @Override
    protected Object random() {
        final int[] dimensions = IntStream.generate(() -> doubleBetween(min, max).intValue()).limit(dimension).toArray();
        return generateArray(dimensions);
    }

    private Object generateArray(int... dimensions) {
        final Class<?> aClass = toWrapper(elementRandomizer.next().getClass());
        final Object array = Array.newInstance(aClass, dimensions);
        final int[] shiftedDimensions = Arrays.copyOfRange(dimensions, 1, dimensions.length);
        final Supplier<Object> supplier = dimensions.length == 1 ? elementRandomizer::next : () -> generateArray(shiftedDimensions);
        for (int i = 0; i < dimensions[0]; i++) {
            Array.set(array, i, supplier.get());
        }
        return array;
    }

    private Class<?> toWrapper(Class<?> clazz) {
        if (clazz == Integer.class)
            return int.class;
        if (clazz == Long.class)
            return long.class;
        if (clazz == Boolean.class)
            return boolean.class;
        if (clazz == Byte.class)
            return byte.class;
        if (clazz == Character.class)
            return char.class;
        if (clazz == Float.class)
            return float.class;
        if (clazz == Double.class)
            return double.class;
        if (clazz == Short.class)
            return short.class;
        throw new IllegalArgumentException("Unsupported class: " + clazz);
    }
}
