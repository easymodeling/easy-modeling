package xyz.v2my.easymodeling.randomizer;

import java.util.Random;

public abstract class GenericRandomizer<T> implements Randomizer {

    protected static final Random random = new Random();

    /**
     * Generate a random double value between given min and max.
     *
     * @param min inclusive lower bound
     * @param max exclusive upper bound, should be greater than or equal to min
     * @return a random double between min (inclusive) and max (exclusive)
     */
    public Double doubleBetween(double min, double max) {
        final double value = random.nextDouble() * (max - min) + min;
        if (value <= min) {
            return min;
        } else if (value > max) {
            return max;
        } else {
            return value;
        }
    }
}
