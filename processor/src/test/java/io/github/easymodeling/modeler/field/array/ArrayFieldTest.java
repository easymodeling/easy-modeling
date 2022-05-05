package io.github.easymodeling.modeler.field.array;

import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.CodeBlock;
import io.github.easymodeling.modeler.field.Container;
import io.github.easymodeling.modeler.field.FieldTest;
import io.github.easymodeling.modeler.field.PlainField;
import io.github.easymodeling.modeler.field.datetime.InstantField;
import io.github.easymodeling.modeler.field.number.IntegerField;
import io.github.easymodeling.modeler.helper.FieldPatternFactory;
import io.github.easymodeling.randomizer.array.ArrayRandomizer;
import io.github.easymodeling.randomizer.datetime.InstantRandomizer;
import io.github.easymodeling.randomizer.number.IntegerRandomizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class ArrayFieldTest extends FieldTest {

    private PlainField<Integer> integerField;

    @BeforeEach
    @Override
    protected void setUp() {
        fieldCustomization = FieldPatternFactory.one(FIELD_NAME).minSize(2).maxSize(5).min(1.).max(3.).build();
        integerField = new IntegerField().create(fieldCustomization);
        typeName = ArrayTypeName.of(Integer.class);
        modelField = new ArrayField(typeName, fieldCustomization, integerField);
    }

    @Override
    protected void should_generate_initializer() {
        final CodeBlock initializer = modelField.initializer();

        assertThat(initializer)
                .hasToString("new " + $(ArrayRandomizer.class) + "<>(" + integerField.initializer() + ", 2, 5)");
    }

    @Test
    void should_not_invoke_create() {
        final Throwable throwable = catchThrowable(() -> new ArrayField(typeName, fieldCustomization, integerField).create(fieldCustomization));

        assertThat(throwable).isInstanceOf(UnsupportedOperationException.class);
    }

    @Nested
    class ArrayOfArrayTest {

        @Test
        void should_generate_statement_of_array() {
            final PlainField<Integer> integerField = new IntegerField().create(fieldCustomization);
            final Container arrayField = new ArrayField(ArrayTypeName.of(Integer.class), fieldCustomization, integerField);

            final CodeBlock initialValue = arrayField.initialValue();

            final String arrayRandomizer = "new " + $(ArrayRandomizer.class) + "<>(new " + $(IntegerRandomizer.class) + "(1.0, 3.0), 2, 5)";
            assertThat(initialValue).hasToString(arrayRandomizer + ".next()");
        }

        @Test
        void should_generate_statement_of_matrix() {
            final PlainField<Integer> integerField = new IntegerField().create(fieldCustomization);
            final Container arrayField = new ArrayField(ArrayTypeName.get(Integer[].class), fieldCustomization, integerField);
            final Container matrixField = new ArrayField(ArrayTypeName.get(Integer[][].class), fieldCustomization, arrayField);

            final CodeBlock initialValue = matrixField.initialValue();

            final String arrayRandomizer = "new " + $(ArrayRandomizer.class) + "<>(new " + $(IntegerRandomizer.class) + "(1.0, 3.0), 2, 5)";
            final String matrixRandomizer = "new " + $(ArrayRandomizer.class) + "<>(" + arrayRandomizer + ", 2, 5)";
            assertThat(initialValue).hasToString(matrixRandomizer + ".next()");
        }

        @Test
        void should_generate_statement_of_cube() {
            final PlainField<Instant> instantField = new InstantField().create(fieldCustomization);
            final Container arrayField = new ArrayField(ArrayTypeName.get(Instant[].class), fieldCustomization, instantField);
            final Container matrixField = new ArrayField(ArrayTypeName.get(Instant[][].class), fieldCustomization, arrayField);
            final Container cubeField = new ArrayField(ArrayTypeName.get(Instant[][][].class), fieldCustomization, matrixField);

            final CodeBlock initialValue = cubeField.initialValue();

            final String arrayRandomizer = "new " + $(ArrayRandomizer.class) + "<>(new " + $(InstantRandomizer.class) + "(0L, 2147483647000L), 2, 5)";
            final String matrixRandomizer = "new " + $(ArrayRandomizer.class) + "<>(" + arrayRandomizer + ", 2, 5)";
            final String cubeRandomizer = "new " + $(ArrayRandomizer.class) + "<>(" + matrixRandomizer + ", 2, 5)";
            assertThat(initialValue).hasToString(cubeRandomizer + ".next()");
        }
    }
}
