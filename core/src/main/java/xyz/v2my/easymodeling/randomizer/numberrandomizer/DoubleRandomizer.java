package xyz.v2my.easymodeling.randomizer.numberrandomizer;


import xyz.v2my.easymodeling.randomizer.NumberRandomizer;

public class DoubleRandomizer extends NumberRandomizer<Double> {

    @Override
    public Double next(long min, long max) {
        return doubleBetween(min, max);
    }
}
