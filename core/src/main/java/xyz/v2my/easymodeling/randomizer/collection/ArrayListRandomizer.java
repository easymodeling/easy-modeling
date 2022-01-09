package xyz.v2my.easymodeling.randomizer.collection;

import xyz.v2my.easymodeling.randomizer.GenericRandomizer;
import xyz.v2my.easymodeling.randomizer.Randomizer;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ArrayListRandomizer<E> extends GenericRandomizer<ArrayList<E>> {

    // FIXME: 09.01.22 remove the duplication with ListRandomizer

    protected final Randomizer<E> elementRandomizer;

    protected final int min;

    protected final int max;

    public ArrayListRandomizer(Randomizer<E> elementRandomizer, int min, int max) {
        this.elementRandomizer = elementRandomizer;
        this.min = min;
        this.max = max;
    }

    @Override
    protected ArrayList<E> random() {
        int size = doubleBetween(min, max).intValue();
        return Stream.generate(elementRandomizer::next).limit(size).collect(Collectors.toCollection(ArrayList::new));
    }

}
