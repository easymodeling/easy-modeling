package xyz.v2my.easymodeling.modeler.provider;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import xyz.v2my.easymodeling.modeler.field.ModelField;
import xyz.v2my.easymodeling.modeler.field.UnknownField;
import xyz.v2my.easymodeling.modeler.helper.FieldPatternFactory;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class ProvideUnknownFieldTest extends ModelFieldProviderTest {

    @Nested
    class GenerateUnknownFieldTest {

        @ParameterizedTest
        @ValueSource(classes = {void.class, Void.class})
        void should_get_unknown_field_for_unsupported_built_in_type(Class<?> unsupportedClass) {
            final ModelField field = modelFieldProvider.provide(TypeName.get(unsupportedClass), FieldPatternFactory.any());

            assertThat(field).isInstanceOf(UnknownField.class);
        }

        @Test
        void should_get_unknown_field_for_supported_generic_type_with_primitive_array_as_type_parameter() {
            final ModelField field = modelFieldProvider.provide(ParameterizedTypeName.get(SomeGeneric.class, int[].class), FieldPatternFactory.any());

            assertThat(field).isInstanceOf(UnknownField.class);
        }

        @SuppressWarnings("InnerClassMayBeStatic")
        private class SomeGeneric<T> {
        }
    }

    @Nested
    class NotGenerateUnknownFieldTest {

        @ParameterizedTest
        @ValueSource(classes = {Integer.class, Instant.class, Integer[].class, Double[][].class})
        void should_not_generate_unknown_field_for_generic_type_with_type_parameters_other_than_primitive_array(Class<?> type) {
            final ModelField field = modelFieldProvider.provide(ParameterizedTypeName.get(Optional.class, type), FieldPatternFactory.any());

            assertThat(field).isNotInstanceOf(UnknownField.class);
        }

        @Test
        void should_not_generate_unknown_field_for_customer_type() {
            final ModelField field = modelFieldProvider.provide(ClassName.get(SomeType.class), FieldPatternFactory.any());

            assertThat(field).isNotInstanceOf(UnknownField.class);
        }

        @SuppressWarnings("InnerClassMayBeStatic")
        private class SomeType {
        }
    }
}
