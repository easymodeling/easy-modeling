package xyz.v2my.easymodeling.randomizer;

import java.util.Random;

public abstract class GenericRandomizer<T> implements Randomizer {

    protected static final Random random = new Random();

    public static boolean aBoolean() {
        return random.nextBoolean();
    }

    public static char aChar() {
        return (char) random.nextInt(Character.MAX_VALUE);
    }

    protected static long longBetween(long min, long max) {
        if (max < min) {
            throw new IllegalArgumentException("upper bound should not be less than lower bound");
        }
        if (max == min) {
            return min;
        }
        return (random.nextInt() >>> 1) % (max - min) + min;
    }

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
