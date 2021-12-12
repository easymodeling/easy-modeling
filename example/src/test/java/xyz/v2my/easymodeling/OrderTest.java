package xyz.v2my.easymodeling;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class OrderTest {

    @Test
    void should_generate_order() {
        final Order order = new EMOrderBuilder().id("some-id").unitPrice(BigDecimal.TEN).build();

        assertThat(order).isNotNull();
        assertThat(order.getUnitPrice()).isEqualTo(BigDecimal.TEN);
        assertThat(order.getAmount()).isEqualTo(0L);
        assertThat(order.getCreationTime()).isNull();
    }
}
