package xyz.v2my.easymodeling.randomizer;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ArrayRandomizer extends GenericRandomizer<Stream<?>> {

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
