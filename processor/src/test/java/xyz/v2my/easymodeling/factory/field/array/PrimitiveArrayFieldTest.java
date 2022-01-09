package xyz.v2my.easymodeling.factory.field.array;

import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xyz.v2my.easymodeling.factory.field.FieldTest;
import xyz.v2my.easymodeling.factory.field.PlainField;
import xyz.v2my.easymodeling.factory.field.number.IntegerField;
import xyz.v2my.easymodeling.factory.helper.FieldWrapperFactory;
import xyz.v2my.easymodeling.randomizer.array.PrimitiveArrayRandomizer;

import javax.lang.model.element.Modifier;

import static org.assertj.core.api.Assertions.assertThat;

class PrimitiveArrayFieldTest extends FieldTest {

    private PlainField<Integer> integerField;

    @BeforeEach
    void setUp() {
        fieldWrapper = FieldWrapperFactory.one(FIELD_NAME).minSize(3).maxSize(8).build();
        integerField = new IntegerField(fieldWrapper);
        typeName = ArrayTypeName.of(int[].class);
        modelField = new PrimitiveArrayField((ArrayTypeName) typeName, fieldWrapper, integerField);
    }

    @Test
    void should_generate_initial_value() {
        final CodeBlock initialValue = modelField.initialValue();

        assertThat(initialValue.toString()).isEqualTo(
                "(int[][]) new " + $(PrimitiveArrayRandomizer.class) + "(" + integerField.initializer().toString() + ", 2, 3, 8).next()");
    }

    @Test
    @Override
    protected void should_generate_builder_setter() {
        final MethodSpec setter = modelField.setter();

        assertThat(setter.name).isEqualTo(FIELD_NAME);
        assertThat(setter.returnType.toString()).isEqualTo("Builder");
        assertThat(setter.modifiers).containsExactly(Modifier.PUBLIC);
    }
}
