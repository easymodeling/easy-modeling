package io.github.easymodeling.randomizer.number;

import java.math.BigDecimal;

public class BigDecimalRandomizer extends NumberRandomizer<BigDecimal> {

    public BigDecimalRandomizer(double min, double max) {
        super(min, max);
    }

    public BigDecimalRandomizer(BigDecimal constant) {
        super(constant);
    }

    @Override
    public BigDecimal random() {
        return BigDecimal.valueOf(doubleBetween(min, max));
    }
}
