package xyz.v2my.easymodeling.randomizer.numberrandomizer;


import xyz.v2my.easymodeling.randomizer.NumberRandomizer;

public class FloatRandomizer extends NumberRandomizer<Float> {

    @Override
    public Float next(double min, double max) {
        return doubleBetween(min, max).floatValue();
    }
}
