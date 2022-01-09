package xyz.v2my.easymodeling.randomizer.collection;

import xyz.v2my.easymodeling.randomizer.Randomizer;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListRandomizer<E> extends AbstractListRandomizer<List<E>, E> {

    public ListRandomizer(Randomizer<E> elementRandomizer, int minSize, int maxSize) {
        super(elementRandomizer, minSize, maxSize);
    }

    @Override
    protected List<E> random() {
        int size = doubleBetween(minSize, maxSize).intValue();
        return Stream.generate(elementRandomizer::next).limit(size).collect(Collectors.toList());
    }

    @Override
    protected Collector<E, ?, List<E>> collector() {
        return Collectors.toList();
    }
}
