package xyz.v2my.easymodeling;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class BoxedPrimitiveTypeModelTest {

    @Test
    void should_create_test_model_and_build_string_field() {
        final var model = EMBoxedPrimitiveTypeModel.builder().build();

        assertNotNull(model);
        assertNotNull(model.getAnInt());
    }
}
