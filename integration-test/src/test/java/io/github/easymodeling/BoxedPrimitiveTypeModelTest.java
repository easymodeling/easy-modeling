package io.github.easymodeling;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BoxedPrimitiveTypeModelTest {

    @Test
    void should_create_test_model_and_build_string_field() {
        final BoxedPrimitiveTypeModel model = BoxedPrimitiveTypeModelModeler.builder().build();

        assertThat(model).isNotNull();
        assertThat(model.getAnInt()).isNotNull();
    }
}
