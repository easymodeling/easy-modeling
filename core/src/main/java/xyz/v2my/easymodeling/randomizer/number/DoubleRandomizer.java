package xyz.v2my.easymodeling.randomizer.number;


public class DoubleRandomizer extends NumberRandomizer<Double> {

    public DoubleRandomizer(double min, double max) {
        super(min, max);
    }

    @Override
    public Double next() {
        return doubleBetween(min, max);
    }
}
