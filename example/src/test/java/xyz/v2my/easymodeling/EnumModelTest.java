package xyz.v2my.easymodeling;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class EnumModelTest {

    @Test
    void should_generate_enum_field() {
        final EnumModel next = EnumModelModeler.next();

        assertNotNull(next);
        assertNotNull(next.enumExample);
        assertNotNull(next.enumExamples);
        assertNotNull(next.listOfEnumExamples);
        assertNotNull(next.node);
    }
}
