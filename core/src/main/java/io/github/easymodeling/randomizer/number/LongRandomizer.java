package io.github.easymodeling.randomizer.number;


public class LongRandomizer extends NumberRandomizer<Long> {

    public LongRandomizer(double min, double max) {
        super(min, max);
    }

    public LongRandomizer(Long constant) {
        super(constant);
    }

    @Override
    public Long random() {
        return doubleBetween(min, max).longValue();
    }
}
