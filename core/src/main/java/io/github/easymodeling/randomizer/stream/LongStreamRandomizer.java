package io.github.easymodeling.randomizer.stream;

import io.github.easymodeling.randomizer.number.LongRandomizer;

import java.util.stream.LongStream;

public class LongStreamRandomizer extends AbstractStreamRandomizer<LongStream, Long> {

    public LongStreamRandomizer(LongRandomizer elementRandomizer, int minSize, int maxSize) {
        super(elementRandomizer, minSize, maxSize);
    }

    @Override
    protected LongStream random() {
        return originalStream().mapToLong(Long::intValue);
    }
}
