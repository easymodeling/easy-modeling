package xyz.v2my.easymodeling.randomizer.collection;

import xyz.v2my.easymodeling.randomizer.GenericRandomizer;
import xyz.v2my.easymodeling.randomizer.Randomizer;

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

    protected CollectionRandomizer() {
    }

    @Override
    protected C random() {
        int size = doubleBetween(minSize, maxSize).intValue();
        return upstream().limit(size).collect(Collectors.toCollection(collectionFactory()));
    }

    protected Stream<E> originalStream() {
        return Stream.generate(elementRandomizer::next);
    }

    protected abstract Stream<E> upstream();

    protected abstract Supplier<C> collectionFactory();
}
