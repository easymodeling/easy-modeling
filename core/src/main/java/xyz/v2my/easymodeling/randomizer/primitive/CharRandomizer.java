package xyz.v2my.easymodeling.randomizer.primitive;

import xyz.v2my.easymodeling.randomizer.GenericRandomizer;

public class CharRandomizer extends GenericRandomizer<Character> {

    public Character next() {
        return (char) random.nextInt(Character.MAX_VALUE);
    }
}
