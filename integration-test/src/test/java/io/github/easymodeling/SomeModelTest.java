package io.github.easymodeling;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SomeModelTest {

    @Test
    void should_populate_nested_model() {
        final SomeModel next = SomeModelModeler.next();

        assertThat(next).isNotNull();
        assertThat(next.someNestedModel).isNotNull();
        assertThat(next.someNestedModel.string).isNotNull();
    }
}
