package xyz.v2my.easymodeling.randomizer.stream;

import xyz.v2my.easymodeling.randomizer.number.DoubleRandomizer;

import java.util.stream.DoubleStream;

public class DoubleStreamRandomizer extends AbstractStreamRandomizer<DoubleStream, Double> {

    public DoubleStreamRandomizer(DoubleRandomizer elementRandomizer, int minSize, int maxSize) {
        super(elementRandomizer, minSize, maxSize);
    }

    @Override
    protected DoubleStream random() {
        return originalStream().mapToDouble(Double::intValue);
    }
}
