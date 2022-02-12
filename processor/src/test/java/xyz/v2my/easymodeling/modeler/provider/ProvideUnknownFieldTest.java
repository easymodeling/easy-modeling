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
            final TypeName typeName = TypeName.get(unsupportedClass);
            final TypeMirrorMock typeMirror = new TypeMirrorMock();
            final ModelField field = modelFieldProvider.provide(typeName, typeMirror, FieldPatternFactory.any());

            assertThat(field).isInstanceOf(UnknownField.class);
        }

        @Test
        void should_get_unknown_field_for_supported_generic_type_with_primitive_array_as_type_parameter() {
            final ParameterizedTypeName typeName = ParameterizedTypeName.get(SomeGeneric.class, int[].class);
            final TypeMirrorMock typeMirror = new TypeMirrorMock();
            final ModelField field = modelFieldProvider.provide(typeName, typeMirror, FieldPatternFactory.any());

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
            final ParameterizedTypeName typeName = ParameterizedTypeName.get(Optional.class, type);
            final TypeMirrorMock typeMirror = new TypeMirrorMock();
            final ModelField field = modelFieldProvider.provide(typeName, typeMirror, FieldPatternFactory.any());

            assertThat(field).isNotInstanceOf(UnknownField.class);
        }

        @Test
        void should_not_generate_unknown_field_for_customer_type() {
            final ClassName typeName = ClassName.get(SomeType.class);
            final TypeMirrorMock typeMirror = new TypeMirrorMock();
            final ModelField field = modelFieldProvider.provide(typeName, typeMirror, FieldPatternFactory.any());

            assertThat(field).isNotInstanceOf(UnknownField.class);
        }

        @SuppressWarnings("InnerClassMayBeStatic")
        private class SomeType {
        }
    }
}
