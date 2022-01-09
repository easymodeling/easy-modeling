package xyz.v2my.easymodeling.randomizer.collection;

import xyz.v2my.easymodeling.randomizer.GenericRandomizer;
import xyz.v2my.easymodeling.randomizer.Randomizer;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListRandomizer<E> extends GenericRandomizer<List<E>> {

    protected final Randomizer<E> elementRandomizer;

    protected final int min;

    protected final int max;

    public ListRandomizer(Randomizer<E> elementRandomizer, int min, int max) {
        this.elementRandomizer = elementRandomizer;
        this.min = min;
        this.max = max;
    }

    @Override
    protected List<E> random() {
        int size = doubleBetween(min, max).intValue();
        return Stream.generate(elementRandomizer::next).limit(size).collect(Collectors.toList());
    }
}
