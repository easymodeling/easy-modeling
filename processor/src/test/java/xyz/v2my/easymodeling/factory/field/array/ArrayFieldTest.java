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
import xyz.v2my.easymodeling.factory.helper.FieldPatternFactory;
import xyz.v2my.easymodeling.randomizer.array.ArrayRandomizer;
import xyz.v2my.easymodeling.randomizer.datetime.InstantRandomizer;
import xyz.v2my.easymodeling.randomizer.number.IntegerRandomizer;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class ArrayFieldTest extends FieldTest {

    private PlainField<Integer> integerField;

    @BeforeEach
    @Override
    protected void setUp() {
        fieldPattern = FieldPatternFactory.one(FIELD_NAME).minSize(2).maxSize(5).min(1.).max(3.).build();
        integerField = new IntegerField().create(fieldPattern);
        typeName = ArrayTypeName.of(Integer.class);
        modelField = new ArrayField(typeName, fieldPattern, integerField);
    }

    @Override
    protected void should_generate_initializer() {
        final CodeBlock initializer = modelField.initializer();

        assertThat(initializer)
                .hasToString("new " + $(ArrayRandomizer.class) + "<>(" + integerField.initializer() + ", 2, 5)");
    }

    @Test
    void should_not_invoke_create() {
        final Throwable throwable = catchThrowable(() -> new ArrayField(typeName, fieldPattern, integerField).create(fieldPattern));

        assertThat(throwable).isInstanceOf(UnsupportedOperationException.class);
    }

    @Nested
    class ArrayOfArrayTest {

        @Test
        void should_generate_statement_of_array() {
            final PlainField<Integer> integerField = new IntegerField().create(fieldPattern);
            final Container arrayField = new ArrayField(ArrayTypeName.of(Integer.class), fieldPattern, integerField);

            final CodeBlock initialValue = arrayField.initialValue();

            final String arrayRandomizer = "new " + $(ArrayRandomizer.class) + "<>(new " + $(IntegerRandomizer.class) + "(1.0, 3.0), 2, 5)";
            assertThat(initialValue).hasToString(arrayRandomizer + ".next()");
        }

        @Test
        void should_generate_statement_of_matrix() {
            final PlainField<Integer> integerField = new IntegerField().create(fieldPattern);
            final Container arrayField = new ArrayField(ArrayTypeName.get(Integer[].class), fieldPattern, integerField);
            final Container matrixField = new ArrayField(ArrayTypeName.get(Integer[][].class), fieldPattern, arrayField);

            final CodeBlock initialValue = matrixField.initialValue();

            final String arrayRandomizer = "new " + $(ArrayRandomizer.class) + "<>(new " + $(IntegerRandomizer.class) + "(1.0, 3.0), 2, 5)";
            final String matrixRandomizer = "new " + $(ArrayRandomizer.class) + "<>(" + arrayRandomizer + ", 2, 5)";
            assertThat(initialValue).hasToString(matrixRandomizer + ".next()");
        }

        @Test
        void should_generate_statement_of_cube() {
            final PlainField<Instant> instantField = new InstantField().create(fieldPattern);
            final Container arrayField = new ArrayField(ArrayTypeName.get(Instant[].class), fieldPattern, instantField);
            final Container matrixField = new ArrayField(ArrayTypeName.get(Instant[][].class), fieldPattern, arrayField);
            final Container cubeField = new ArrayField(ArrayTypeName.get(Instant[][][].class), fieldPattern, matrixField);

            final CodeBlock initialValue = cubeField.initialValue();

            final String arrayRandomizer = "new " + $(ArrayRandomizer.class) + "<>(new " + $(InstantRandomizer.class) + "(0L, 2147483647000L), 2, 5)";
            final String matrixRandomizer = "new " + $(ArrayRandomizer.class) + "<>(" + arrayRandomizer + ", 2, 5)";
            final String cubeRandomizer = "new " + $(ArrayRandomizer.class) + "<>(" + matrixRandomizer + ", 2, 5)";
            assertThat(initialValue).hasToString(cubeRandomizer + ".next()");
        }
    }
}
