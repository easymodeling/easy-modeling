package io.github.easymodeling.modeler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FieldCustomizationTest {

    private FieldCustomization customization;

    @BeforeEach
    void setUp() {
        customization = new FieldCustomization("class-name", "field-name", 0d, 0d, 0d, false, false, false, "string", false, "string", "string", false, false, "string", 0, 0, 0, false);
    }

    @Test
    void should_match_customization_with_field_name_and_class_name() {
        assertThat(customization.matches("class-name", "field-name")).isTrue();

        assertThat(customization.matches("x-class-name", "field-name")).isFalse();
        assertThat(customization.matches("class-name", "x-field-name")).isFalse();
        assertThat(customization.matches("x-class-name", "x-field-name")).isFalse();
    }
}
