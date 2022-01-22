package xyz.v2my.easymodeling.randomizer.stream;

import xyz.v2my.easymodeling.randomizer.Randomizer;

import java.util.stream.Stream;

public class StreamRandomizer<E> extends AbstractStreamRandomizer<Stream<E>, E> {

    public StreamRandomizer(Randomizer<E> elementRandomizer, int minSize, int maxSize) {
        super(elementRandomizer, minSize, maxSize);
    }

    @Override
    protected Stream<E> random() {
        return originalStream();
    }
}
