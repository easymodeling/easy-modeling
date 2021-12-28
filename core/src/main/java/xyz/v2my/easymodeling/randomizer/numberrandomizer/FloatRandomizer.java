package xyz.v2my.easymodeling.randomizer.numberrandomizer;


import xyz.v2my.easymodeling.randomizer.NumberRandomizer;

public class FloatRandomizer extends NumberRandomizer<Float> {

    @Override
    public Float next(long min, long max) {
        return doubleBetween(min, max).floatValue();
    }
}
