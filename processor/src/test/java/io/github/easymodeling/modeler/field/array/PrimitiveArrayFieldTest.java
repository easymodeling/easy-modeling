package io.github.easymodeling.modeler.field.array;

import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.CodeBlock;
import io.github.easymodeling.modeler.field.ModelFieldTest;
import io.github.easymodeling.modeler.field.PlainField;
import io.github.easymodeling.modeler.field.number.IntegerField;
import io.github.easymodeling.modeler.helper.FieldPatternFactory;
import io.github.easymodeling.randomizer.array.PrimitiveArrayRandomizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class PrimitiveArrayFieldTest extends ModelFieldTest {

    private PlainField<Integer> integerField;

    @BeforeEach
    @Override
    protected void setUp() {
        fieldCustomization = FieldPatternFactory.one(FIELD_NAME).minSize(3).maxSize(8).build();
        integerField = new IntegerField().create(fieldCustomization);
        typeName = ArrayTypeName.of(int[].class);
        modelField = new PrimitiveArrayField((ArrayTypeName) typeName, fieldCustomization, integerField);
    }

    @Test
    void should_generate_initial_value() {
        final CodeBlock initializer = modelField.initializer();
        final CodeBlock initialValue = modelField.initialValue();

        assertThat(initialValue).isEqualTo(CodeBlock.of("(int[][]) $L.next()", initializer));
    }

    @Override
    protected void should_generate_initializer() {
        final CodeBlock initializer = modelField.initializer();

        assertThat(initializer).hasToString(
                "new " + $(PrimitiveArrayRandomizer.class) + "(" + integerField.initializer() + ", 2, 3, 8)");
    }

    @Test
    void should_not_invoke_create() {
        final Throwable throwable = catchThrowable(() -> modelField.create(fieldCustomization));

        assertThat(throwable).isInstanceOf(UnsupportedOperationException.class);
    }
}
