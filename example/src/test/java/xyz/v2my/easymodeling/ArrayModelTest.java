package xyz.v2my.easymodeling;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ArrayModelTest {

    @Test
    void should_generate_arrays() {
        final ArrayModel model = EMArrayModel.next();

        assertNotNull(model);
        assertNotNull(model.string);
        assertNotNull(model.intArray);
        assertNotNull(model.matrix);
        assertNotNull(model.crazyInstantArray);

        for (Integer integer : model.intArray) {
            assertNotNull(integer);
        }

        final boolean notAllZero = Arrays.stream(model.primitiveIntArray).anyMatch(i -> i != 0);
        assertTrue(notAllZero);
    }
}
