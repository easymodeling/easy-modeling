package io.github.easymodeling.randomizer.collection;

import io.github.easymodeling.randomizer.Randomizer;

import java.util.Set;

public abstract class AbstractSetRandomizer<C extends Set<E>, E> extends CollectionRandomizer<C, E> {

    protected AbstractSetRandomizer(Randomizer<E> elementRandomizer, int maxSize) {
        super(elementRandomizer, 1, maxSize);
    }
}
