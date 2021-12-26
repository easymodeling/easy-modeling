package xyz.v2my.easymodeling;

import java.util.Random;

public abstract class Randomizer {

    private static final Random random = new Random();

    public static boolean aBoolean() {
        return random.nextBoolean();
    }

    public static byte aByte() {
        return (byte) nonNegativeLessThan(Byte.MAX_VALUE);
    }

    public static byte aByte(long max) {
        return (byte) nonNegativeLessThan(max, Byte.MAX_VALUE);
    }

    public static short aShort() {
        return (short) nonNegativeLessThan(Short.MAX_VALUE);
    }

    public static short aShort(long max) {
        return (short) nonNegativeLessThan(max, Short.MAX_VALUE);
    }

    public static int anInt() {
        return (int) nonNegativeLessThan(Integer.MAX_VALUE);
    }

    public static int anInt(long max) {
        return (int) nonNegativeLessThan(max, Integer.MAX_VALUE);
    }

    public static long aLong() {
        return nonNegativeLessThan(Long.MAX_VALUE);
    }

    public static long aLong(long max) {
        return nonNegativeLessThan(max, Long.MAX_VALUE);
    }

    public static float aFloat() {
        return random.nextFloat();
    }

    public static float aFloat(int max) {
        return random.nextFloat() * max;
    }

    public static double aDouble(long max) {
        return random.nextDouble() * max;
    }

    public static double aDouble() {
        return random.nextDouble();
    }

    public static char aChar() {
        return (char) random.nextInt(Character.MAX_VALUE);
    }

    private static long nonNegativeLessThan(long width) {
        return nonNegativeLessThan(width, width);
    }

    private static long nonNegativeLessThan(long bound, long width) {
        if (bound <= 0) {
            throw new IllegalArgumentException("bound must be positive");
        }
        return (random.nextLong() >>> 1) % Math.min(bound, width);
    }
}
