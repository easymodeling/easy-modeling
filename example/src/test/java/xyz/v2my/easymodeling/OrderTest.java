package xyz.v2my.easymodeling;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class OrderTest {

    @Test
    void should_generate_order() {
        final Order order = new EMOrderBuilder().id("some-id").unitPrice(BigDecimal.TEN).build();

        assertNotNull(order);
        assertEquals(BigDecimal.TEN, order.getUnitPrice());
        assertEquals(0L, order.getAmount());
        assertNull(order.getCreationTime());
    }
}
