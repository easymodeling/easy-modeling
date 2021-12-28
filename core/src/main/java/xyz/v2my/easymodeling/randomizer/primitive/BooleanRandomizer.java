package xyz.v2my.easymodeling.randomizer.primitive;

import xyz.v2my.easymodeling.randomizer.GenericRandomizer;

public class BooleanRandomizer extends GenericRandomizer<Boolean> {

    public Boolean next() {
        return random.nextBoolean();
    }
}