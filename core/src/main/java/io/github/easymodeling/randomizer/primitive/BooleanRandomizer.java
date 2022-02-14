package io.github.easymodeling.randomizer.primitive;

import io.github.easymodeling.randomizer.GenericRandomizer;

public class BooleanRandomizer extends GenericRandomizer<Boolean> {

    public Boolean random() {
        return random.nextBoolean();
    }
}
