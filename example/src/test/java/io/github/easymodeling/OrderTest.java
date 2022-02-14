package io.github.easymodeling;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OrderTest {

    @Test
    void should_generate_order() {
        final Order order = OrderModeler.builder().id("some-id").unitPrice(BigDecimal.TEN).build();

        assertNotNull(order);
        assertEquals(BigDecimal.TEN, order.getUnitPrice());
        assertNotNull(order.getCreationTime());

        assertNotNull(order.orderLines);
        assertEquals(order.orderLines.get(0),
                order.orderLines.get(0)
                        .order.orderLines.get(0)
                        .order.orderLines.get(0)
                        .order.orderLines.get(0)
                        .order.orderLines.get(0)
                        .order.orderLines.get(0)
        );
    }
}
