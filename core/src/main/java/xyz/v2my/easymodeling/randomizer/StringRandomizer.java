package xyz.v2my.easymodeling.randomizer;

public class StringRandomizer extends Randomizer {

    public static String aString() {
        return random.ints(0x00, 0x7F + 1)
                .limit(random.nextInt(48))
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
