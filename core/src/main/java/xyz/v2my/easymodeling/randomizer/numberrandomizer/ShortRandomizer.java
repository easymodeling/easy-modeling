package xyz.v2my.easymodeling.randomizer.numberrandomizer;


import xyz.v2my.easymodeling.randomizer.NumberRandomizer;

public class ShortRandomizer extends NumberRandomizer<Short> {

    public ShortRandomizer(double min, double max) {
        super(min, max);
    }

    @Override
    public Short next() {
        return doubleBetween(min, max).shortValue();
    }
}
