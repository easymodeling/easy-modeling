package xyz.v2my.easymodeling.randomizer;

import java.lang.reflect.Array;

public class ArrayRandomizer<E> extends GenericRandomizer<E[]> {

    private final Randomizer<E> elementRandomizer;

    private final Class<E> elementClass;

    public ArrayRandomizer(Randomizer<E> elementRandomizer, Class<E> elementClass) {
        this.elementRandomizer = elementRandomizer;
        this.elementClass = elementClass;
    }

    @Override
    public E[] next() {
        final int size = doubleBetween(1, 10).intValue();
        @SuppressWarnings("unchecked")
        E[] array = (E[]) Array.newInstance(elementClass, size);
        for (int i = 0; i < size; i++) {
            array[i] = elementRandomizer.next();
        }
        return array;
    }
}
