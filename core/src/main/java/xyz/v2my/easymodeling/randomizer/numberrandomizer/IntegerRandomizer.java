package xyz.v2my.easymodeling.randomizer.numberrandomizer;


import xyz.v2my.easymodeling.randomizer.NumberRandomizer;

public class IntegerRandomizer extends NumberRandomizer<Integer> {

    public IntegerRandomizer(double min, double max) {
        super(min, max);
    }

    @Override
    public Integer next() {
        return doubleBetween(min, max).intValue();
    }
}
