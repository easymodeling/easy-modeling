package io.github.easymodeling.modeler.provider;

import com.squareup.javapoet.TypeName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import io.github.easymodeling.modeler.FieldPattern;
import io.github.easymodeling.modeler.field.ModelField;
import io.github.easymodeling.modeler.field.datetime.InstantField;
import io.github.easymodeling.modeler.field.number.DoubleField;
import io.github.easymodeling.modeler.field.number.FloatField;
import io.github.easymodeling.modeler.field.number.IntegerField;
import io.github.easymodeling.modeler.field.number.LongField;
import io.github.easymodeling.modeler.field.number.ShortField;
import io.github.easymodeling.modeler.field.primitive.BooleanField;
import io.github.easymodeling.modeler.field.primitive.CharField;
import io.github.easymodeling.modeler.field.string.StringBuilderField;
import io.github.easymodeling.modeler.field.string.StringField;
import io.github.easymodeling.modeler.helper.FieldPatternFactory;
import io.github.easymodeling.modeler.provider.mock.Factory;

import javax.lang.model.type.TypeMirror;
import java.time.Instant;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class PlainFieldProviderTest extends ModelFieldProviderTest {

    @ParameterizedTest
    @MethodSource({"plainFields", "unboxedFields"})
    void should_provide_field(TypeMirror typeMirror, Class<? extends ModelField> fieldClass) {
        final FieldPattern fieldPattern = FieldPatternFactory.any();

        final ModelField plainField = modelFieldProvider.provide(typeMirror, fieldPattern);

        assertThat(plainField).isInstanceOf(fieldClass);
    }

    public static Stream<Arguments> plainFields() {
        return Stream.of(
                Arguments.of(Factory.clazz(Short.class), ShortField.class),
                Arguments.of(Factory.clazz(Integer.class), IntegerField.class),
                Arguments.of(Factory.clazz(Long.class), LongField.class),
                Arguments.of(Factory.clazz(Float.class), FloatField.class),
                Arguments.of(Factory.clazz(Double.class), DoubleField.class),
                Arguments.of(Factory.clazz(Boolean.class), BooleanField.class),
                Arguments.of(Factory.clazz(Character.class), CharField.class),
                Arguments.of(Factory.clazz(String.class), StringField.class),
                Arguments.of(Factory.clazz(StringBuilder.class), StringBuilderField.class),
                Arguments.of(Factory.clazz(Instant.class), InstantField.class)
        );
    }

    public static Stream<Arguments> unboxedFields() {
        return Stream.of(
                Arguments.of(Factory.primitive(TypeName.SHORT), ShortField.class),
                Arguments.of(Factory.primitive(TypeName.INT), IntegerField.class),
                Arguments.of(Factory.primitive(TypeName.LONG), LongField.class),
                Arguments.of(Factory.primitive(TypeName.FLOAT), FloatField.class),
                Arguments.of(Factory.primitive(TypeName.DOUBLE), DoubleField.class),
                Arguments.of(Factory.primitive(TypeName.BOOLEAN), BooleanField.class),
                Arguments.of(Factory.primitive(TypeName.CHAR), CharField.class)
        );
    }
}
