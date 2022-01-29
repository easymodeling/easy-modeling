package xyz.v2my.easymodeling.modeler.provider;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import xyz.v2my.easymodeling.modeler.FieldPattern;
import xyz.v2my.easymodeling.modeler.field.ModelField;
import xyz.v2my.easymodeling.modeler.field.datetime.InstantField;
import xyz.v2my.easymodeling.modeler.field.number.DoubleField;
import xyz.v2my.easymodeling.modeler.field.number.FloatField;
import xyz.v2my.easymodeling.modeler.field.number.IntegerField;
import xyz.v2my.easymodeling.modeler.field.number.LongField;
import xyz.v2my.easymodeling.modeler.field.number.ShortField;
import xyz.v2my.easymodeling.modeler.field.primitive.BooleanField;
import xyz.v2my.easymodeling.modeler.field.primitive.CharField;
import xyz.v2my.easymodeling.modeler.field.string.StringBuilderField;
import xyz.v2my.easymodeling.modeler.field.string.StringField;
import xyz.v2my.easymodeling.modeler.helper.FieldPatternFactory;

import java.time.Instant;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class PlainFieldProviderTest extends ModelFieldProviderTest {

    @ParameterizedTest
    @MethodSource({"plainFields", "unboxedFields"})
    void should_provide_field(TypeName typeName, Class<? extends ModelField> fieldClass) {
        final FieldPattern fieldPattern = FieldPatternFactory.any();

        final ModelField plainField = modelFieldProvider.provide(typeName, fieldPattern);

        assertThat(plainField).isInstanceOf(fieldClass);
    }

    public static Stream<Arguments> plainFields() {
        return Stream.of(
                Arguments.of(ClassName.get(Short.class), ShortField.class),
                Arguments.of(ClassName.get(Integer.class), IntegerField.class),
                Arguments.of(ClassName.get(Long.class), LongField.class),
                Arguments.of(ClassName.get(Float.class), FloatField.class),
                Arguments.of(ClassName.get(Double.class), DoubleField.class),
                Arguments.of(ClassName.get(Boolean.class), BooleanField.class),
                Arguments.of(ClassName.get(Character.class), CharField.class),
                Arguments.of(ClassName.get(String.class), StringField.class),
                Arguments.of(ClassName.get(StringBuilder.class), StringBuilderField.class),
                Arguments.of(ClassName.get(Instant.class), InstantField.class)
        );
    }

    public static Stream<Arguments> unboxedFields() {
        return Stream.of(
                Arguments.of(TypeName.SHORT, ShortField.class),
                Arguments.of(TypeName.INT, IntegerField.class),
                Arguments.of(TypeName.LONG, LongField.class),
                Arguments.of(TypeName.FLOAT, FloatField.class),
                Arguments.of(TypeName.DOUBLE, DoubleField.class),
                Arguments.of(TypeName.BOOLEAN, BooleanField.class),
                Arguments.of(TypeName.CHAR, CharField.class)
        );
    }
}
