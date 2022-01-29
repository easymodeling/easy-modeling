package xyz.v2my.easymodeling.modeler.field.collection;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.ParameterizedTypeName;
import org.junit.jupiter.api.BeforeEach;
import xyz.v2my.easymodeling.modeler.field.FieldTest;
import xyz.v2my.easymodeling.modeler.field.PlainField;
import xyz.v2my.easymodeling.modeler.field.number.IntegerField;
import xyz.v2my.easymodeling.modeler.helper.FieldPatternFactory;
import xyz.v2my.easymodeling.randomizer.collection.ArrayListRandomizer;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

class ArrayListFieldTest extends FieldTest {

    private PlainField<Integer> integerField;

    @BeforeEach
    @Override
    protected void setUp() {
        fieldPattern = FieldPatternFactory.one(FIELD_NAME).build();
        integerField = new IntegerField().create(fieldPattern);
        typeName = ParameterizedTypeName.get(ArrayList.class, Integer.class);
        modelField = new ArrayListField().create(fieldPattern, integerField);
    }

    @Override
    protected void should_generate_initializer() {
        final CodeBlock initializer = modelField.initializer();

        assertThat(initializer).hasToString(
                "new " + $(ArrayListRandomizer.class) + "<>(" + integerField.initializer() + ", 1, 20)");
    }
}
