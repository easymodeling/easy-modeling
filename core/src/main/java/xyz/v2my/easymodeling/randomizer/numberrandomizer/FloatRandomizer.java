package xyz.v2my.easymodeling.randomizer.numberrandomizer;


import xyz.v2my.easymodeling.randomizer.NumberRandomizer;

public class FloatRandomizer extends NumberRandomizer<Float> {

    public FloatRandomizer(double min, double max) {
        super(min, max);
    }

    @Override
    public Float next() {
        return doubleBetween(min, max).floatValue();
    }
}
