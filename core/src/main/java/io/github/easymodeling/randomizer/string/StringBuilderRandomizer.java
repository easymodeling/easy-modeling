package io.github.easymodeling.randomizer.string;

public class StringBuilderRandomizer extends CharSequenceRandomizer<StringBuilder> {

    public StringBuilderRandomizer(long min, long max, int charRange) {
        super(min, max, charRange);
    }

    public StringBuilderRandomizer(StringBuilder constant) {
        super(constant);
    }

    @Override
    public StringBuilder random() {
        return nextStringBuilder();
    }
}
