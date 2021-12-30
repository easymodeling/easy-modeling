package xyz.v2my.easymodeling.randomizer.string;

public class StringRandomizer extends CharSequenceRandomizer<String> {

    public StringRandomizer(long min, long max, int charset) {
        super(min, max, charset);
    }

    public String next() {
        return nextStringBuilder().toString();
    }
}
