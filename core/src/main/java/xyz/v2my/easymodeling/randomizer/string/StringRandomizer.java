package xyz.v2my.easymodeling.randomizer.string;

public class StringRandomizer extends CharSequenceRandomizer<String> {

    public StringRandomizer(long min, long max, int charset) {
        super(min, max, charset);
    }

    public StringRandomizer(String constant) {
        super(constant);
    }

    public String random() {
        return nextStringBuilder().toString();
    }
}
