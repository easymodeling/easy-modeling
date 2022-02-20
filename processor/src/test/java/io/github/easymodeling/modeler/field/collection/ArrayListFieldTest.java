package io.github.easymodeling.modeler.field.collection;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.ParameterizedTypeName;
import org.junit.jupiter.api.BeforeEach;
import io.github.easymodeling.modeler.field.FieldTest;
import io.github.easymodeling.modeler.field.PlainField;
import io.github.easymodeling.modeler.field.number.IntegerField;
import io.github.easymodeling.modeler.helper.FieldPatternFactory;
import io.github.easymodeling.randomizer.collection.ArrayListRandomizer;

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