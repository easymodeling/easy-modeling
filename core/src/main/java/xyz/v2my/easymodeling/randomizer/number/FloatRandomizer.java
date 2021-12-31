package xyz.v2my.easymodeling.randomizer.number;


public class FloatRandomizer extends NumberRandomizer<Float> {

    public FloatRandomizer(double min, double max) {
        super(min, max);
    }

    public FloatRandomizer(Float constant) {
        super(constant);
    }

    @Override
    public Float random() {
        return doubleBetween(min, max).floatValue();
    }
}
