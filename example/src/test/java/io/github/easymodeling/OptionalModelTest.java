package io.github.easymodeling;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class OptionalModelTest {

    @Test
    void should_populate_optional_model() {
        final OptionalModel model = OptionalModelModeler.next();

        assertNotNull(model);
        assertNotNull(model.optionalInteger);
        assertNotNull(model.optionalInteger.get());
    }
}