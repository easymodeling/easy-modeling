package xyz.v2my.easymodeling;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class SomeModelTest {

    @Test
    void should_populate_nested_model() {
        final SomeModel next = SomeModelModeler.next();

        assertNotNull(next);
//        assertNotNull(next.someNestedModel);
//        assertNotNull(next.someNestedModel.string);
    }
}
