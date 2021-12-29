package xyz.v2my.easymodeling.randomizer.numberrandomizer;


import xyz.v2my.easymodeling.randomizer.NumberRandomizer;

public class DoubleRandomizer extends NumberRandomizer<Double> {

    public DoubleRandomizer(double min, double max) {
        super(min, max);
    }

    @Override
    public Double next() {
        return doubleBetween(min, max);
    }
}
