package xyz.v2my.easymodeling.randomizer.numberrandomizer;


import xyz.v2my.easymodeling.randomizer.NumberRandomizer;

public class LongRandomizer extends NumberRandomizer<Long> {

    @Override
    public Long next(double min, double max) {
        return doubleBetween(min, max).longValue();
    }
}
