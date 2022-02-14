package io.github.easymodeling.randomizer.collection;

import io.github.easymodeling.randomizer.Randomizer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ListRandomizer<E> extends AbstractListRandomizer<List<E>, E> {

    public ListRandomizer(Randomizer<E> elementRandomizer, int minSize, int maxSize) {
        super(elementRandomizer, minSize, maxSize);
    }

    @Override
    protected Supplier<List<E>> collectionFactory() {
        return ArrayList::new;
    }
}
