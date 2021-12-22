package xyz.v2my.easymodeling;

import java.util.Random;

public abstract class Randomizer {

    private static final Random random = new Random();

    public static boolean aBoolean() {
        return random.nextBoolean();
    }

    public static byte aByte() {
        return (byte) random.nextInt();
    }

    public static short aShort() {
        return (short) random.nextInt();
    }

    public static int anInt() {
        return random.nextInt();
    }

    public static long aLong() {
        return random.nextLong();
    }

    public static float aFloat() {
        return random.nextFloat();
    }

    public static double aDouble() {
        return random.nextDouble();
    }

    public static char aChar() {
        return (char) random.nextInt();
    }
}
