package xyz.v2my.easymodeling.randomizer.number;

import xyz.v2my.easymodeling.randomizer.GenericRandomizer;

public abstract class NumberRandomizer<T> extends GenericRandomizer<T> {

    protected double min;

    protected double max;

    public NumberRandomizer(double min, double max) {
        this.min = min;
        this.max = max;
    }

    public NumberRandomizer(T constant) {
        super(constant);
    }
}
