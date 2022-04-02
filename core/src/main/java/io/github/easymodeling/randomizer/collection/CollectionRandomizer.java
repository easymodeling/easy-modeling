package io.github.easymodeling.randomizer.collection;

import io.github.easymodeling.randomizer.GenericRandomizer;
import io.github.easymodeling.randomizer.Randomizer;

import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class CollectionRandomizer<C extends Collection<E>, E> extends GenericRandomizer<C> {

    protected Randomizer<E> elementRandomizer;

    protected int minSize;

    protected int maxSize;

    protected CollectionRandomizer(Randomizer<E> elementRandomizer, int minSize, int maxSize) {
        this.elementRandomizer = elementRandomizer;
        this.minSize = minSize;
        this.maxSize = maxSize;
    }

    @Override
    protected C random() {
        int size = doubleBetween(minSize, maxSize).intValue();
        return Stream.generate(elementRandomizer::next)
                .limit(size)
                .collect(Collectors.toCollection(collectionFactory()));
    }

    protected abstract Supplier<C> collectionFactory();
}
