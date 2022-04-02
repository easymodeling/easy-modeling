package io.github.easymodeling.randomizer.collection;

import io.github.easymodeling.randomizer.Randomizer;

import java.util.Set;
import java.util.TreeSet;
import java.util.function.Supplier;

public class TreeSetRandomizer<E> extends AbstractSetRandomizer<Set<E>, E> {

    public TreeSetRandomizer(Randomizer<E> elementRandomizer, int maxSize) {
        super(elementRandomizer, maxSize);
    }

    @Override
    protected Supplier<Set<E>> collectionFactory() {
        return TreeSet::new;
    }
}
