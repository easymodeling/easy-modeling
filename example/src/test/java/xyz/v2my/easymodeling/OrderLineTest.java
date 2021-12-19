package xyz.v2my.easymodeling;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OrderLineTest {

    @Test
    void should_generate_order_line() {
        OrderLine orderLine = new EMOrderLineBuilder().id("some-id").build();

        assertNotNull(orderLine);
        assertEquals(orderLine.getId(), "some-id");
    }
}
