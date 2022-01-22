package xyz.v2my.easymodeling.randomizer.collection;

import xyz.v2my.easymodeling.randomizer.Randomizer;

import java.util.List;
import java.util.stream.Stream;

public abstract class AbstractListRandomizer<C extends List<E>, E> extends CollectionRandomizer<C, E> {

    protected AbstractListRandomizer(Randomizer<E> elementRandomizer, int minSize, int maxSize) {
        super(elementRandomizer, minSize, maxSize);
    }

    protected Stream<E> upstream() {
        return originalStream();
    }
}
