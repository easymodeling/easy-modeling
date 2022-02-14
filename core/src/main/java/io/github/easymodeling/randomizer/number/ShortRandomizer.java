package io.github.easymodeling.randomizer.number;


public class ShortRandomizer extends NumberRandomizer<Short> {

    public ShortRandomizer(double min, double max) {
        super(min, max);
    }

    public ShortRandomizer(Short constant) {
        super(constant);
    }

    @Override
    public Short random() {
        return doubleBetween(min, max).shortValue();
    }
}
