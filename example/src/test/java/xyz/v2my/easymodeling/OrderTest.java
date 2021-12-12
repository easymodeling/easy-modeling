package xyz.v2my.easymodeling;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class OrderTest {

    @Test
    void should_() {
        new EMOrderBuilder().id("some-id").unitPrice(BigDecimal.TEN);

    }
}
