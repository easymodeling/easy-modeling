package xyz.v2my.easymodeling.randomizer;

import java.util.Random;

public abstract class GenericRandomizer {

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
}
