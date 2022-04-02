package io.github.easymodeling.randomizer;

import java.security.SecureRandom;
import java.util.Optional;
import java.util.Random;

public abstract class GenericRandomizer<T> implements Randomizer<T> {

    protected static final Random random = new SecureRandom();

    protected T constant;

    protected GenericRandomizer() {
    }

    protected GenericRandomizer(T constant) {
        this.constant = constant;
    }

    @Override
    public final T next() {
        return constant().orElseGet(this::random);
    }

    private Optional<T> constant() {
        return Optional.ofNullable(constant);
    }

    protected abstract T random();

    /**
     * Generate a random double value between given min and max.
     *
     * @param min inclusive lower bound
     * @param max exclusive upper bound, should be greater than or equal to min
     * @return a random double between min (inclusive) and max (exclusive)
     */
    protected Double doubleBetween(double min, double max) {
        final double value = random.nextDouble() * (max - min) + min;
        if (value <= min) {
            return min;
        } else if (value > max) {
            return max;
        } else {
            return value;
        }
    }

    protected boolean oneThirdTruth() {
        return random.nextInt(3) == 0;
    }
}
