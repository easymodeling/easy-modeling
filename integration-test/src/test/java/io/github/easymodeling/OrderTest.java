package io.github.easymodeling;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class OrderTest {

    @Test
    void should_generate_order() {
        final Order order = OrderModeler.builder().id("some-id").unitPrice(BigDecimal.TEN).build();

        assertThat(order).isNotNull();
        assertThat(order.getUnitPrice()).isEqualTo(BigDecimal.TEN);

        assertThat(order.getCreationTime()).isNotNull();

        assertThat(order.orderLines).isNotNull();
    }
}
