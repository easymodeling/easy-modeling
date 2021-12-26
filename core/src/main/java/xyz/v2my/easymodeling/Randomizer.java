package xyz.v2my.easymodeling;

import java.util.Random;

public abstract class Randomizer {

    private static final Random random = new Random();

    public static boolean aBoolean() {
        return random.nextBoolean();
    }

    public static byte aByte(long min, long max) {
        return (byte) intBetween(min, max);
    }

    public static short aShort(long min, long max) {
        return (short) intBetween(min, max);
    }

    public static int anInt(long min, long max) {
        return (int) intBetween(min, max);
    }

    public static long aLong(long min, long max) {
        return (long) aDouble(min, max);
    }

    public static float aFloat(long min, long max) {
        return random.nextFloat() * (max - min) + min;
    }

    public static double aDouble(long min, long max) {
        return random.nextDouble() * (max - min) + min;
    }

    public static char aChar() {
        return (char) random.nextInt(Character.MAX_VALUE);
    }

    private static long intBetween(long min, long max) {
        if (max < min) {
            throw new IllegalArgumentException("upper bound should not be less than lower bound");
        }
        if (max == min) {
            return min;
        }
        return (random.nextInt() >>> 1) % (max - min) + min;
    }
}
