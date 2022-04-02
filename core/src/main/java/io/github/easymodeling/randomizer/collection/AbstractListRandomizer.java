package io.github.easymodeling.randomizer.collection;

import io.github.easymodeling.randomizer.Randomizer;

import java.util.List;

public abstract class AbstractListRandomizer<C extends List<E>, E> extends CollectionRandomizer<C, E> {

    protected AbstractListRandomizer(Randomizer<E> elementRandomizer, int minSize, int maxSize) {
        super(elementRandomizer, minSize, maxSize);
    }
}
