package io.github.easymodeling.randomizer.stream;

import io.github.easymodeling.randomizer.GenericRandomizer;
import io.github.easymodeling.randomizer.Randomizer;

import java.util.stream.BaseStream;
import java.util.stream.Stream;

public abstract class AbstractStreamRandomizer<S extends BaseStream<E, S>, E> extends GenericRandomizer<S> {

    protected Randomizer<E> elementRandomizer;

    protected int minSize;

    protected int maxSize;

    protected AbstractStreamRandomizer(Randomizer<E> elementRandomizer, int minSize, int maxSize) {
        this.elementRandomizer = elementRandomizer;
        this.minSize = minSize;
        this.maxSize = maxSize;
    }

    protected Stream<E> originalStream() {
        int size = doubleBetween(minSize, maxSize).intValue();
        return Stream.generate(elementRandomizer::next).limit(size);
    }
}
