package io.github.easymodeling.randomizer.collection;

import io.github.easymodeling.randomizer.Randomizer;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class HashSetRandomizer<E> extends AbstractSetRandomizer<Set<E>, E> {

    public HashSetRandomizer(Randomizer<E> elementRandomizer, int maxSize) {
        super(elementRandomizer, maxSize);
    }

    @Override
    protected Supplier<Set<E>> collectionFactory() {
        return HashSet::new;
    }
}
