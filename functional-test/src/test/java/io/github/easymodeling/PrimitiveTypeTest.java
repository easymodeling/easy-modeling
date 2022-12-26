package io.github.easymodeling;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PrimitiveTypeTest {

    @Test
    void should_create_test_model() {
        final PrimitiveTypeModel testModel = PrimitiveTypeModelModeler.builder().build();

        assertThat(testModel).isNotNull();
        assertThat(testModel.getaString()).isNotNull();
    }

    @Test
    void should_create_test_model_and_build_string_field() {
        final PrimitiveTypeModel testModel = PrimitiveTypeModelModeler.builder().aString("some-string").build();

        assertThat(testModel).isNotNull();
        assertThat(testModel.getaString()).isEqualTo("some-string");
    }

    @Test
    void should_create_test_model_with_next_method() {
        final PrimitiveTypeModel testModel = PrimitiveTypeModelModeler.next();

        assertThat(testModel).isNotNull();
    }
}
