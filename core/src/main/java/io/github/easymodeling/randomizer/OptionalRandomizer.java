package io.github.easymodeling.randomizer;

import java.util.Optional;

public class OptionalRandomizer<E> extends GenericRandomizer<Optional<E>> {

    private final Randomizer<E> valueRandomizer;

    private final Boolean allowEmpty;

    public OptionalRandomizer(Randomizer<E> valueRandomizer, Boolean allowEmpty) {
        this.valueRandomizer = valueRandomizer;
        this.allowEmpty = allowEmpty;
    }

    @Override
    protected Optional<E> random() {
        return (allowEmpty && oneThirdTruth()) ? Optional.empty() : Optional.of(valueRandomizer.next());
    }
}
