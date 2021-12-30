package xyz.v2my.easymodeling;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ArrayModelTest {

    @Test
    void should_generate_arrays() {
        final ArrayModel model = EMArrayModel.next();

        assertNotNull(model);
        assertNotNull(model.string);
        assertNotNull(model.intArray);
        assertNotNull(model.matrix);
        assertNotNull(model.crazyInstantArray);
    }
}
