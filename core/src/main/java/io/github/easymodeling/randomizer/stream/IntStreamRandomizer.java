package io.github.easymodeling.randomizer.stream;

import io.github.easymodeling.randomizer.number.IntegerRandomizer;

import java.util.stream.IntStream;

public class IntStreamRandomizer extends AbstractStreamRandomizer<IntStream, Integer> {

    public IntStreamRandomizer(IntegerRandomizer elementRandomizer, int minSize, int maxSize) {
        super(elementRandomizer, minSize, maxSize);
    }

    @Override
    protected IntStream random() {
        return originalStream().mapToInt(Integer::intValue);
    }
}
