package io.github.easymodeling;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class OrderLineTest {

    @Test
    void should_generate_order_line() {
        OrderLine orderLine = OrderLineModeler.builder().id("some-id").build();

        assertThat(orderLine).isNotNull();
        assertThat(orderLine.getId()).isEqualTo("some-id");
    }

    @Test
    void should_create_builder() {
        final Order order = OrderModeler.builder().id("some-id").unitPrice(BigDecimal.TEN).build();

        assertThat(order.getUnitPrice()).isEqualTo(BigDecimal.TEN);
    }
}
