package xyz.v2my.easymodeling.randomizer.collection;

import xyz.v2my.easymodeling.randomizer.Randomizer;

import java.util.LinkedList;
import java.util.function.Supplier;

public class LinkedListRandomizer<E> extends AbstractListRandomizer<LinkedList<E>, E> {

    public LinkedListRandomizer(Randomizer<E> elementRandomizer, int minSize, int maxSize) {
        super(elementRandomizer, minSize, maxSize);
    }

    @Override
    protected Supplier<LinkedList<E>> collectionFactory() {
        return LinkedList::new;
    }
}
