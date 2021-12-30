package xyz.v2my.easymodeling.randomizer.collection;

import xyz.v2my.easymodeling.randomizer.GenericRandomizer;
import xyz.v2my.easymodeling.randomizer.Randomizer;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ArrayRandomizer extends GenericRandomizer<Object> {

    private final Randomizer<?> elementRandomizer;

    public ArrayRandomizer(Randomizer<?> elementRandomizer) {
        this.elementRandomizer = elementRandomizer;
    }

    @Override
    public Stream<?> next() {
        final int size = doubleBetween(1, 10).intValue();
        return IntStream.range(0, size).mapToObj(i -> elementRandomizer.next());
    }
}
