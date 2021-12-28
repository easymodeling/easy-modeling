package xyz.v2my.easymodeling.randomizer.numberrandomizer;


import xyz.v2my.easymodeling.randomizer.NumberRandomizer;

public class IntegerRandomizer extends NumberRandomizer<Integer> {

    @Override
    public Integer next(double min, double max) {
        return doubleBetween(min, max).intValue();
    }
}
