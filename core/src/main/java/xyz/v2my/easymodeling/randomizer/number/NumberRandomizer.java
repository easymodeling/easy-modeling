package xyz.v2my.easymodeling.randomizer.number;

import xyz.v2my.easymodeling.randomizer.GenericRandomizer;

public abstract class NumberRandomizer<T> extends GenericRandomizer<T> {

    protected final double min;

    protected final double max;

    public NumberRandomizer(double min, double max) {
        this.min = min;
        this.max = max;
    }
}
