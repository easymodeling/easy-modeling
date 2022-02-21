package io.github.easymodeling.randomizer.string;

import io.github.easymodeling.randomizer.GenericRandomizer;

public abstract class CharSequenceRandomizer<T extends CharSequence> extends GenericRandomizer<T> {

    // @formatter:off
    public static final int ELSE          = 0b0100;
    public static final int NUMERIC       = 0b0001;
    public static final int ALPHABETIC    = 0b0010;
    public static final int ALPHANUMERIC  = 0b0011;
    public static final int ANY           = 0b0111;
    // @formatter:on

    protected CharSequenceRandomizer(long min, long max, int charRange) {
        this.min = min;
        this.max = max;
        this.charRange = charRange;
    }

    protected CharSequenceRandomizer(T constant) {
        super(constant);
    }

    protected long min;

    protected long max;

    protected int charRange;

    private static int isAlphabet(int c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') ? ALPHABETIC : 0;
    }

    private static int isNumeric(int c) {
        return c >= '0' && c <= '9' ? NUMERIC : 0;
    }

    protected StringBuilder nextStringBuilder() {
        return random.ints(0x00, 0x7F + 1)
                .filter(c -> ((CharSequenceRandomizer.isAlphabet(c) | CharSequenceRandomizer.isNumeric(c) | ELSE) & this.charRange) != 0)
                .limit(doubleBetween(this.min, this.max).longValue())
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append);
    }
}
