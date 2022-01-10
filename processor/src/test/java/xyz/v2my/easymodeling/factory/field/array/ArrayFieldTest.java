package xyz.v2my.easymodeling.factory.field.array;

import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.CodeBlock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import xyz.v2my.easymodeling.factory.field.Container;
import xyz.v2my.easymodeling.factory.field.FieldTest;
import xyz.v2my.easymodeling.factory.field.PlainField;
import xyz.v2my.easymodeling.factory.field.datetime.InstantField;
import xyz.v2my.easymodeling.factory.field.number.IntegerField;
import xyz.v2my.easymodeling.factory.helper.FieldWrapperFactory;
import xyz.v2my.easymodeling.randomizer.array.ArrayRandomizer;
import xyz.v2my.easymodeling.randomizer.datetime.InstantRandomizer;
import xyz.v2my.easymodeling.randomizer.number.IntegerRandomizer;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class ArrayFieldTest extends FieldTest {

    private PlainField<Integer> integerField;

    @BeforeEach
    @Override
    protected void setUp() {
        fieldWrapper = FieldWrapperFactory.one(FIELD_NAME).minSize(2).maxSize(5).min(1.).max(3.).build();
        integerField = new IntegerField(fieldWrapper);
        typeName = ArrayTypeName.of(Integer.class);
        modelField = new ArrayField(typeName, fieldWrapper, integerField);
    }

    @Test
    @Override
    protected void should_generate_initializer() {
        final CodeBlock initializer = modelField.initializer();

        assertThat(initializer.toString())
                .isEqualTo("new " + $(ArrayRandomizer.class) + "<>(" + integerField.initializer() + ", 2, 5)");
    }

    @Nested
    class ArrayOfArrayTest {

        @Test
        void should_generate_statement_of_array() {
            final PlainField<Integer> integerField = new IntegerField(fieldWrapper);
            final Container arrayField = new ArrayField(ArrayTypeName.of(Integer.class), fieldWrapper, integerField);

            final CodeBlock initialValue = arrayField.initialValue();

            final String arrayRandomizer = "new " + $(ArrayRandomizer.class) + "<>(new " + $(IntegerRandomizer.class) + "(1.0, 3.0), 2, 5)";
            assertThat(initialValue.toString()).isEqualTo(arrayRandomizer + ".next()");
        }

        @Test
        void should_generate_statement_of_matrix() {
            final PlainField<Integer> integerField = new IntegerField(fieldWrapper);
            final Container arrayField = new ArrayField(ArrayTypeName.get(Integer[].class), fieldWrapper, integerField);
            final Container matrixField = new ArrayField(ArrayTypeName.get(Integer[][].class), fieldWrapper, arrayField);

            final CodeBlock initialValue = matrixField.initialValue();

            final String arrayRandomizer = "new " + $(ArrayRandomizer.class) + "<>(new " + $(IntegerRandomizer.class) + "(1.0, 3.0), 2, 5)";
            final String matrixRandomizer = "new " + $(ArrayRandomizer.class) + "<>(" + arrayRandomizer + ", 2, 5)";
            assertThat(initialValue.toString()).isEqualTo(matrixRandomizer + ".next()");
        }

        @Test
        void should_generate_statement_of_cube() {
            final PlainField<Instant> instantField = new InstantField(fieldWrapper);
            final Container arrayField = new ArrayField(ArrayTypeName.get(Instant[].class), fieldWrapper, instantField);
            final Container matrixField = new ArrayField(ArrayTypeName.get(Instant[][].class), fieldWrapper, arrayField);
            final Container cubeField = new ArrayField(ArrayTypeName.get(Instant[][][].class), fieldWrapper, matrixField);

            final CodeBlock initialValue = cubeField.initialValue();

            final String arrayRandomizer = "new " + $(ArrayRandomizer.class) + "<>(new " + $(InstantRandomizer.class) + "(0L, 2147483647000L), 2, 5)";
            final String matrixRandomizer = "new " + $(ArrayRandomizer.class) + "<>(" + arrayRandomizer + ", 2, 5)";
            final String cubeRandomizer = "new " + $(ArrayRandomizer.class) + "<>(" + matrixRandomizer + ", 2, 5)";
            assertThat(initialValue.toString()).isEqualTo(cubeRandomizer + ".next()");
        }
    }
}
