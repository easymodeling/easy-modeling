package xyz.v2my.easymodeling.randomizer.number;


public class LongRandomizer extends NumberRandomizer<Long> {

    public LongRandomizer(double min, double max) {
        super(min, max);
    }

    @Override
    public Long next() {
        return doubleBetween(min, max).longValue();
    }
}
