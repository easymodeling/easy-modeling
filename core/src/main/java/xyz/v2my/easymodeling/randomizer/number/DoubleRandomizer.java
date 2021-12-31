package xyz.v2my.easymodeling.randomizer.number;


public class DoubleRandomizer extends NumberRandomizer<Double> {

    public DoubleRandomizer(double min, double max) {
        super(min, max);
    }

    public DoubleRandomizer(Double constant) {
        super(constant);
    }

    @Override
    public Double random() {
        return doubleBetween(min, max);
    }
}
