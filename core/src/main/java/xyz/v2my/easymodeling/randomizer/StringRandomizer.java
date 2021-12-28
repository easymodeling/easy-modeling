package xyz.v2my.easymodeling.randomizer;

public class StringRandomizer extends Randomizer {

    public static String aString(long min, long max) {
        return random.ints(0x00, 0x7F + 1)
                .limit(longBetween(min, max))
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
