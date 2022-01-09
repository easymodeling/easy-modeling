package xyz.v2my.easymodeling.randomizer.collection;

import xyz.v2my.easymodeling.randomizer.GenericRandomizer;
import xyz.v2my.easymodeling.randomizer.Randomizer;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractListRandomizer<C extends List<E>, E> extends GenericRandomizer<C> {

    protected final Randomizer<E> elementRandomizer;

    protected final int minSize;

    protected final int maxSize;

    public AbstractListRandomizer(Randomizer<E> elementRandomizer, int minSize, int maxSize) {
        this.elementRandomizer = elementRandomizer;
        this.minSize = minSize;
        this.maxSize = maxSize;
    }

    @Override
    protected C random() {
        int size = doubleBetween(minSize, maxSize).intValue();
        return Stream.generate(elementRandomizer::next).limit(size)
                .collect(Collectors.toCollection(collectionFactory()));
    }

    protected abstract Supplier<C> collectionFactory();
}
