package xyz.v2my.easymodeling.randomizer;

public class StringRandomizer extends GenericRandomizer {

    // @formatter:off
    public static final int ELSE          = 0b0100;
    public static final int NUMERIC       = 0b0001;
    public static final int ALPHABETIC    = 0b0010;
    public static final int ALPHANUMERIC  = 0b0011;
    public static final int RANDOM        = 0b0111;
    // @formatter:on

    public static String aString(long min, long max, int charset) {
        return random.ints(0x00, 0x7F + 1)
                .filter(c -> ((isAlphabet(c) | isNumeric(c) | ELSE) & charset) != 0)
                .limit(longBetween(min, max))
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    private static int isAlphabet(int c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') ? ALPHABETIC : 0;
    }

    private static int isNumeric(int c) {
        return c >= '0' && c <= '9' ? NUMERIC : 0;
    }
}
