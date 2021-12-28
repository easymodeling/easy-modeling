package xyz.v2my.easymodeling.randomizer.numberrandomizer;


import xyz.v2my.easymodeling.randomizer.NumberRandomizer;

public class ShortRandomizer extends NumberRandomizer<Short> {

    @Override
    public Short next(long min, long max) {
        return doubleBetween(min, max).shortValue();
    }
}
