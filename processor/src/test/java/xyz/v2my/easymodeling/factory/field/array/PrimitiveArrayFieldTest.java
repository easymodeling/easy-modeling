package xyz.v2my.easymodeling.factory.field.array;

import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.CodeBlock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xyz.v2my.easymodeling.factory.field.FieldTest;
import xyz.v2my.easymodeling.factory.field.PlainField;
import xyz.v2my.easymodeling.factory.field.number.IntegerField;
import xyz.v2my.easymodeling.factory.helper.FieldWrapperFactory;
import xyz.v2my.easymodeling.randomizer.array.PrimitiveArrayRandomizer;

import static org.assertj.core.api.Assertions.assertThat;

class PrimitiveArrayFieldTest extends FieldTest {

    private PlainField<Integer> integerField;

    @BeforeEach
    @Override
    protected void setUp() {
        fieldWrapper = FieldWrapperFactory.one(FIELD_NAME).minSize(3).maxSize(8).build();
        integerField = new IntegerField().create(fieldWrapper);
        typeName = ArrayTypeName.of(int[].class);
        modelField = new PrimitiveArrayField((ArrayTypeName) typeName, fieldWrapper, integerField);
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
}
