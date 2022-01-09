package xyz.v2my.easymodeling.randomizer.collection;

import xyz.v2my.easymodeling.randomizer.Randomizer;

import java.util.ArrayList;
import java.util.function.Supplier;

public class ArrayListRandomizer<E> extends AbstractListRandomizer<ArrayList<E>, E> {

    public ArrayListRandomizer(Randomizer<E> elementRandomizer, int minSize, int maxSize) {
        super(elementRandomizer, minSize, maxSize);
    }

    @Override
    protected Supplier<ArrayList<E>> collectionFactory() {
        return ArrayList::new;
    }
}
