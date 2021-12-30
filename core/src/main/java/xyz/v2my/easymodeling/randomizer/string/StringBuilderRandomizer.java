package xyz.v2my.easymodeling.randomizer.string;

public class StringBuilderRandomizer extends CharSequenceRandomizer<StringBuilder> {

    public StringBuilderRandomizer(long min, long max, int charset) {
        super(min, max, charset);
    }

    @Override
    public StringBuilder next() {
        return nextStringBuilder();
    }
}
