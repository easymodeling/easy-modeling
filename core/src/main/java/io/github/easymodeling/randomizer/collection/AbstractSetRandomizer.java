package io.github.easymodeling.randomizer.collection;

import io.github.easymodeling.randomizer.Randomizer;

import java.util.Set;
import java.util.stream.Stream;

public abstract class AbstractSetRandomizer<C extends Set<E>, E> extends CollectionRandomizer<C, E> {

    protected AbstractSetRandomizer(Randomizer<E> elementRandomizer, int minSize, int maxSize) {
        this.elementRandomizer = elementRandomizer;
        this.minSize = minSize;
        this.maxSize = maxSize;
    }

    @Override
    protected Stream<E> upstream() {
        return originalStream().distinct();
    }
}
