package xyz.v2my.easymodeling.randomizer.collection;

import xyz.v2my.easymodeling.randomizer.Randomizer;

import java.util.Set;
import java.util.TreeSet;
import java.util.function.Supplier;

public class TreeSetRandomizer<E> extends AbstractSetRandomizer<Set<E>, E> {

    public TreeSetRandomizer(Randomizer<E> elementRandomizer, int minSize, int maxSize) {
        super(elementRandomizer, minSize, maxSize);
    }

    @Override
    protected Supplier<Set<E>> collectionFactory() {
        return TreeSet::new;
    }
}
