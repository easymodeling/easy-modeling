package io.github.easymodeling.randomizer.number;

import io.github.easymodeling.randomizer.GenericRandomizer;

public abstract class NumberRandomizer<T> extends GenericRandomizer<T> {

    protected double min;

    protected double max;

    protected NumberRandomizer(double min, double max) {
        this.min = min;
        this.max = max;
    }

    protected NumberRandomizer(T constant) {
        super(constant);
    }
}
