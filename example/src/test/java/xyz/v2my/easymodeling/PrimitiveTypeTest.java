package xyz.v2my.easymodeling;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PrimitiveTypeTest {

    @Test
    void should_create_test_model() {
        final PrimitiveTypeModel testModel = PrimitiveTypeModelModeler.builder().build();

        assertNotNull(testModel);
        assertNotNull(testModel.getaString());
    }

    @Test
    void should_create_test_model_and_build_string_field() {
        final PrimitiveTypeModel testModel = PrimitiveTypeModelModeler.builder().aString("some-string").build();

        assertNotNull(testModel);
        assertEquals("some-string", testModel.getaString());
    }

    @Test
    void should_create_test_model_with_next_method() {
        final PrimitiveTypeModel testModel = PrimitiveTypeModelModeler.next();

        assertNotNull(testModel);
    }
}
