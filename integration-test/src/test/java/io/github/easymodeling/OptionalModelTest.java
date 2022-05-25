package io.github.easymodeling;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OptionalModelTest {

    @Test
    void should_populate_optional_model() {
        final OptionalModel model = OptionalModelModeler.next();

        assertThat(model).isNotNull();
        assertThat(model.optionalInteger).isNotNull().isNotEmpty();
        assertThat(model.optionalBigInteger).isNotNull().isNotEmpty();
        assertThat(model.optionalBigDecimal).isNotNull().isNotEmpty();
    }
}
