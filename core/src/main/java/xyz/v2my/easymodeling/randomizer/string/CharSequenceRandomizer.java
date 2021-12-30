package xyz.v2my.easymodeling.randomizer.string;

import xyz.v2my.easymodeling.randomizer.GenericRandomizer;

public abstract class CharSequenceRandomizer<T extends CharSequence> extends GenericRandomizer<T> {

    // @formatter:off
    public static final int ELSE          = 0b0100;
    public static final int NUMERIC       = 0b0001;
    public static final int ALPHABETIC    = 0b0010;
    public static final int ALPHANUMERIC  = 0b0011;
    public static final int RANDOM        = 0b0111;
    // @formatter:on

    protected CharSequenceRandomizer(long min, long max, int charset) {
        this.min = min;
        this.max = max;
        this.charset = charset;
    }

    protected final long min;

    protected final long max;

    protected final int charset;

    private static int isAlphabet(int c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') ? ALPHABETIC : 0;
    }

    private static int isNumeric(int c) {
        return c >= '0' && c <= '9' ? NUMERIC : 0;
    }

    protected StringBuilder nextStringBuilder() {
        return random.ints(0x00, 0x7F + 1)
                .filter(c -> ((CharSequenceRandomizer.isAlphabet(c) | CharSequenceRandomizer.isNumeric(c) | ELSE) & this.charset) != 0)
                .limit(doubleBetween(this.min, this.max).longValue())
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append);
    }
}
