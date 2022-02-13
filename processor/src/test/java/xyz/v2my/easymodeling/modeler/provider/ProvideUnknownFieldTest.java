package xyz.v2my.easymodeling.modeler.provider;

import com.squareup.javapoet.TypeName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import xyz.v2my.easymodeling.modeler.field.ModelField;
import xyz.v2my.easymodeling.modeler.field.UnknownField;
import xyz.v2my.easymodeling.modeler.helper.FieldPatternFactory;
import xyz.v2my.easymodeling.modeler.provider.mock.Factory;

import javax.lang.model.type.TypeMirror;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class ProvideUnknownFieldTest extends ModelFieldProviderTest {

    @Nested
    class GenerateUnknownFieldTest {

        @Test
        void should_get_unknown_field_for_unsupported_built_in_type() {
            final TypeMirror typeMirror = Factory.clazz(Void.class);
            final ModelField field = modelFieldProvider.provide(typeMirror, FieldPatternFactory.any());

            assertThat(field).isInstanceOf(UnknownField.class);
        }

        @Test
        void should_get_unknown_field_for_primitive_void() {
            final TypeMirror typeMirror = Factory.primitive(TypeName.VOID);
            final ModelField field = modelFieldProvider.provide(typeMirror, FieldPatternFactory.any());

            assertThat(field).isInstanceOf(UnknownField.class);
        }

        @ParameterizedTest
        @ValueSource(classes = {Stream.class, List.class})
        void should_get_unknown_field_for_supported_generic_type_with_primitive_array_as_type_parameter(Class<?> containerClass) {
            final TypeMirror typeMirror = Factory.container(containerClass, Factory.array(Factory.primitive(TypeName.INT)));
            final ModelField field = modelFieldProvider.provide(typeMirror, FieldPatternFactory.any());

            assertThat(field).isInstanceOf(UnknownField.class);
        }
    }

    @ParameterizedTest
    @MethodSource("componentTypeProvider")
    void should_not_generate_unknown_field_for_generic_type_with_type_parameters(TypeMirror componentType) {
        final TypeMirror typeMirror = Factory.container(Optional.class, componentType);
        final ModelField field = modelFieldProvider.provide(typeMirror, FieldPatternFactory.any());

        assertThat(field).isNotInstanceOf(UnknownField.class);
    }

    @Test
    void should_not_generate_unknown_field_for_customer_type() {
        final TypeMirror typeMirror = Factory.clazz(SomeType.class);
        final ModelField field = modelFieldProvider.provide(typeMirror, FieldPatternFactory.any());

        assertThat(field).isNotInstanceOf(UnknownField.class);
    }

    @SuppressWarnings("InnerClassMayBeStatic")
    private class SomeType {
    }

    public static Stream<Arguments> componentTypeProvider() {
        return Stream.of(
                Arguments.of(Factory.clazz(Integer.class)),
                Arguments.of(Factory.clazz(Instant.class)),
                Arguments.of(Factory.array(Factory.clazz(Integer.class)))
        );
    }
}
