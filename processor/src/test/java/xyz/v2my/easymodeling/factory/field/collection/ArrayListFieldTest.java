package xyz.v2my.easymodeling.factory.field.collection;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.ParameterizedTypeName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xyz.v2my.easymodeling.factory.field.FieldTest;
import xyz.v2my.easymodeling.factory.field.PlainField;
import xyz.v2my.easymodeling.factory.field.number.IntegerField;
import xyz.v2my.easymodeling.factory.helper.FieldWrapperFactory;
import xyz.v2my.easymodeling.randomizer.collection.ArrayListRandomizer;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

class ArrayListFieldTest extends FieldTest {

    private PlainField<Integer> integerField;

    @BeforeEach
    @Override
    protected void setUp() {
        fieldWrapper = FieldWrapperFactory.one(FIELD_NAME).build();
        integerField = new IntegerField(fieldWrapper);
        typeName = ParameterizedTypeName.get(ArrayList.class, Integer.class);
        modelField = new ArrayListField(fieldWrapper, integerField);
    }

    @Override
    protected void should_generate_initializer() {
        final CodeBlock initializer = modelField.initializer();

        assertThat(initializer.toString()).isEqualTo(
                "new " + $(ArrayListRandomizer.class) + "<>(" + integerField.initializer() + ", 1, 20)");
    }
}
