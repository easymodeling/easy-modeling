package io.github.easymodeling;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class MapModelTest {

    @Test
    void should_populate_map() {
        final MapModel next = MapModelModeler.next();

        assertNotNull(next);
    }
}
