package xyz.v2my.easymodeling.randomizer.number;


public class ShortRandomizer extends NumberRandomizer<Short> {

    public ShortRandomizer(double min, double max) {
        super(min, max);
    }

    @Override
    public Short next() {
        return doubleBetween(min, max).shortValue();
    }
}
