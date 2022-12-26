package io.github.easymodeling;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MapModelTest {

    @Test
    void should_populate_map() {
        final MapModel next = MapModelModeler.next();

        assertThat(next).isNotNull();
    }
}
