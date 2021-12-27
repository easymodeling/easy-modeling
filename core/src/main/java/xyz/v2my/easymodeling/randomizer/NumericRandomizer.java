package xyz.v2my.easymodeling.randomizer;

public class NumericRandomizer extends Randomizer {

    public static byte aByte(long min, long max) {
        return (byte) longBetween(min, max);
    }

    public static short aShort(long min, long max) {
        return (short) longBetween(min, max);
    }

    public static int anInt(long min, long max) {
        return (int) longBetween(min, max);
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

}
