package io.github.easymodeling.modeler;

import io.github.easymodeling.ReflectionUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class FieldCustomizationTest {

    private static final String CANONICAL_NAME = FieldCustomization.class.getCanonicalName() + "#";

    private FieldCustomization customization;

    @BeforeEach
    void setUp() {
        customization = new FieldCustomization(
                "class-name",       // className
                "field-name",       // fieldName
                0d,                 // max
                0d,                 // min
                0d,                 // constant
                false,              // alphanumeric
                false,              // alphabetic
                false,              // numeric
                "string",           // string
                false,              // now
                "string",           // before
                "string",           // after
                false,              // future
                false,              // past
                "",                 // datetime
                0,                  // size
                0,                  // minSize
                0,                  // maxSize
                false               // allowEmpty
        );
    }

    @Test
    void should_match_customization_with_field_name_and_class_name() {
        assertThat(customization.matches("class-name", "field-name")).isTrue();

        assertThat(customization.matches("x-class-name", "field-name")).isFalse();
        assertThat(customization.matches("class-name", "x-field-name")).isFalse();
        assertThat(customization.matches("x-class-name", "x-field-name")).isFalse();
    }

    @Nested
    class WhenParseDatetimeCustomization {

        @Test
        void should_take_empty_datetime_as_not_set() {
            ReflectionUtil.setField(customization, CANONICAL_NAME + "datetime", "");

            assertThat(customization.datetime()).isEmpty();
        }

        @Test
        void should_parse_datetime() {
            ReflectionUtil.setField(customization, CANONICAL_NAME + "datetime", "2020-01-02T12:34:56.789Z");

            assertThat(customization.datetime()).contains(Instant.parse("2020-01-02T12:34:56.789Z"));
        }

        @Test
        void should_get_empty_when_datetime_string_is_mis_formatted() {
            ReflectionUtil.setField(customization, CANONICAL_NAME + "datetime", "some-bad-formatting");

            assertThat(customization.datetime()).isEmpty();
        }
    }
}
