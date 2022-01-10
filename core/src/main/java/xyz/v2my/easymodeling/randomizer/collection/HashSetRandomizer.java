package xyz.v2my.easymodeling.randomizer.collection;

import xyz.v2my.easymodeling.randomizer.Randomizer;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class HashSetRandomizer<E> extends AbstractSetRandomizer<Set<E>, E> {

    public HashSetRandomizer(Randomizer<E> elementRandomizer, int minSize, int maxSize) {
        super(elementRandomizer, minSize, maxSize);
    }

    @Override
    protected Supplier<Set<E>> collectionFactory() {
        return HashSet::new;
    }
}
