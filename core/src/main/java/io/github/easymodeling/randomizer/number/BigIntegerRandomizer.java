package io.github.easymodeling.randomizer.number;

import java.math.BigInteger;

public class BigIntegerRandomizer extends NumberRandomizer<BigInteger> {

    public BigIntegerRandomizer(double min, double max) {
        super(min, max);
    }

    public BigIntegerRandomizer(BigInteger constant) {
        super(constant);
    }

    @Override
    public BigInteger random() {
        return BigInteger.valueOf(doubleBetween(min, max).longValue());
    }
}
