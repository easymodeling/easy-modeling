package io.github.easymodeling.derived;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DerivedTest {

    @Test
    void should_populate_derived_class_fields() {
        final Derived derived = DerivedModeler.next();

        assertThat(derived).isNotNull();
        assertThat(derived.derivedInt).isNotNull();
        assertThat(derived.baseInt).isNotNull();
    }
}
