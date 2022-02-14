package io.github.easymodeling.randomizer.string;

public class StringRandomizer extends CharSequenceRandomizer<String> {

    public StringRandomizer(long min, long max, int charset) {
        super(min, max, charset);
    }

    public StringRandomizer(String constant) {
        super(constant);
    }

    protected String random() {
        return nextStringBuilder().toString();
    }
}
