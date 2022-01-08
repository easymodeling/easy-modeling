package xyz.v2my.easymodeling.factory.field.array;

import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import xyz.v2my.easymodeling.factory.field.FieldTest;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.Container;
import xyz.v2my.easymodeling.factory.field.PlainField;
import xyz.v2my.easymodeling.factory.field.datetime.InstantField;
import xyz.v2my.easymodeling.factory.field.number.IntegerField;
import xyz.v2my.easymodeling.factory.helper.FieldWrapperFactory;
import xyz.v2my.easymodeling.randomizer.array.ArrayRandomizer;
import xyz.v2my.easymodeling.randomizer.datetime.InstantRandomizer;
import xyz.v2my.easymodeling.randomizer.number.IntegerRandomizer;

import javax.lang.model.element.Modifier;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class ArrayFieldTest extends FieldTest {

    private FieldWrapper field;

    @BeforeEach
    void setUp() {
        this.field = FieldWrapperFactory.one("field_name").minSize(2).maxSize(5).min(1.).max(3.).build();
    }

    @Override
    @Test
    protected void should_generate_builder_field() {
        final PlainField<Integer> integerField = new IntegerField().create(ClassName.get(Integer.class), field);
        final Container arrayField = new ArrayField(ArrayTypeName.of(Integer.class), field, integerField);

        final FieldSpec field = arrayField.field();

        assertThat(field.name).contains("field_name");
        assertThat(field.type.toString()).isEqualTo($(Integer.class) + "[]");
        assertThat(field.modifiers).containsExactly(Modifier.PRIVATE);
    }

    @Override
    @Test
    protected void should_generate_builder_setter() {
        final PlainField<Integer> integerField = new IntegerField().create(ClassName.get(Integer.class), field);
        final Container arrayField = new ArrayField(ArrayTypeName.of(Integer.class), field, integerField);

        final MethodSpec setter = arrayField.setter("Builder");

        assertThat(setter.name).isEqualTo("field_name");
        assertThat(setter.returnType.toString()).isEqualTo("Builder");
        assertThat(setter.modifiers).containsExactly(Modifier.PUBLIC);
    }

    @Test
    void should_not_create_field() {
        final Container arrayField = new ArrayField(ArrayTypeName.of(Integer.class), field, null);

        final Throwable throwable = catchThrowable(() -> arrayField.create(ArrayTypeName.of(Integer.class), field, null));

        assertThat(throwable).isInstanceOf(UnsupportedOperationException.class);
    }

    @Nested
    class ArrayOfArrayTest {

        @Test
        void should_generate_statement_of_array() {
            final PlainField<Integer> integerField = new IntegerField().create(ClassName.get(Integer.class), field);
            final Container arrayField = new ArrayField(ArrayTypeName.of(Integer.class), field, integerField);

            final CodeBlock initialValue = arrayField.initialValue();

            final String arrayRandomizer = "new " + $(ArrayRandomizer.class) + "<>(new " + $(IntegerRandomizer.class) + "(1.0, 3.0), 2, 5)";
            assertThat(initialValue.toString()).isEqualTo(arrayRandomizer + ".next()");
        }

        @Test
        void should_generate_statement_of_matrix() {
            final PlainField<Integer> integerField = new IntegerField().create(ClassName.get(Integer.class), field);
            final Container arrayField = new ArrayField(ArrayTypeName.get(Integer[].class), field, integerField);
            final Container matrixField = new ArrayField(ArrayTypeName.get(Integer[][].class), field, arrayField);

            final CodeBlock initialValue = matrixField.initialValue();

            final String arrayRandomizer = "new " + $(ArrayRandomizer.class) + "<>(new " + $(IntegerRandomizer.class) + "(1.0, 3.0), 2, 5)";
            final String matrixRandomizer = "new " + $(ArrayRandomizer.class) + "<>(" + arrayRandomizer + ", 2, 5)";
            assertThat(initialValue.toString()).isEqualTo(matrixRandomizer + ".next()");
        }

        @Test
        void should_generate_statement_of_cube() {
            final PlainField<Instant> instantField = new InstantField().create(ClassName.get(Instant.class), field);
            final Container arrayField = new ArrayField(ArrayTypeName.get(Instant[].class), field, instantField);
            final Container matrixField = new ArrayField(ArrayTypeName.get(Instant[][].class), field, arrayField);
            final Container cubeField = new ArrayField(ArrayTypeName.get(Instant[][][].class), field, matrixField);

            final CodeBlock initialValue = cubeField.initialValue();

            final String arrayRandomizer = "new " + $(ArrayRandomizer.class) + "<>(new " + $(InstantRandomizer.class) + "(0L, 2147483647000L), 2, 5)";
            final String matrixRandomizer = "new " + $(ArrayRandomizer.class) + "<>(" + arrayRandomizer + ", 2, 5)";
            final String cubeRandomizer = "new " + $(ArrayRandomizer.class) + "<>(" + matrixRandomizer + ", 2, 5)";
            assertThat(initialValue.toString()).isEqualTo(cubeRandomizer + ".next()");
        }
    }

}
