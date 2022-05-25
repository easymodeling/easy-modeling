package io.github.easymodeling;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NumericalTypeModelTest {

    @Test
    void should_create_numerical_type_model() {
        final NumericalTypeModel model = NumericalTypeModelModeler.builder().build();

        assertThat(model.getAnInt()).isEqualTo(11);
        assertThat(model.getaShort()).isEqualTo((short) 11);

        assertThat(model.aBigDecimal).isNotNull();
        assertThat(model.aBigInteger).isNotNull();
    }
}
