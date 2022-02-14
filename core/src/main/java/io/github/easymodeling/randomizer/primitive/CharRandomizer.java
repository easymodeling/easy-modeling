package io.github.easymodeling.randomizer.primitive;

import io.github.easymodeling.randomizer.GenericRandomizer;

public class CharRandomizer extends GenericRandomizer<Character> {

    public Character random() {
        return (char) random.nextInt(Character.MAX_VALUE);
    }
}
