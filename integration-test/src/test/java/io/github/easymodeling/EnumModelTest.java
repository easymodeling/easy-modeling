package io.github.easymodeling;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EnumModelTest {

    @Test
    void should_generate_enum_field() {
        final EnumModel next = EnumModelModeler.next();

        assertThat(next).isNotNull();
        assertThat(next.enumExample).isNotNull();
        assertThat(next.enumExamples).isNotNull();
        assertThat(next.listOfEnumExamples).isNotNull();
        assertThat(next.node).isNotNull();
    }
}
