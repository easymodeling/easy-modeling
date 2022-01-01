package xyz.v2my.easymodeling.randomizer;

import java.util.Optional;

public class OptionalRandomizer<E> extends GenericRandomizer<Optional<E>> {

    private final Randomizer<E> valueRandomizer;

    public OptionalRandomizer(Randomizer<E> valueRandomizer) {
        this.valueRandomizer = valueRandomizer;
    }

    @Override
    protected Optional<E> random() {
        return Optional.of(valueRandomizer.next());
    }
}
