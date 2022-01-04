package xyz.v2my.easymodeling.randomizer.array;

import xyz.v2my.easymodeling.randomizer.GenericRandomizer;
import xyz.v2my.easymodeling.randomizer.Randomizer;

import java.lang.reflect.Array;
import java.util.Optional;

public class ArrayRandomizer<E> extends GenericRandomizer<E[]> {

    private final Randomizer<E> elementRandomizer;

    private final int min;

    private final int max;

    private Integer dimension;

    public ArrayRandomizer(Randomizer<E> elementRandomizer, int min, int max) {
        this.elementRandomizer = elementRandomizer;
        this.min = min;
        this.max = max;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected E[] random() {
        if (elementRandomizer instanceof ArrayRandomizer) {
            ((ArrayRandomizer<?>) elementRandomizer).setDimension(randomLength());
        }
        final int length = length();
        final Object array = Array.newInstance(elementType(), length);
        for (int i = 0; i < length; i++) {
            Array.set(array, i, elementRandomizer.next());
        }
        return (E[]) array;
    }

    private Class<?> elementType() {
        final E elementExample = elementRandomizer.next();
        return elementExample.getClass();
    }

    public void setDimension(Integer dimension) {
        this.dimension = dimension;
    }

    private int length() {
        return Optional.ofNullable(dimension).orElseGet(this::randomLength);
    }

    private int randomLength() {
        return doubleBetween(min, max).intValue();
    }
}
