package io.github.easymodeling.modeler.field.collection;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.ParameterizedTypeName;
import io.github.easymodeling.modeler.field.ModelFieldTest;
import io.github.easymodeling.modeler.field.PlainField;
import io.github.easymodeling.modeler.field.number.IntegerField;
import io.github.easymodeling.modeler.helper.FieldPatternFactory;
import io.github.easymodeling.randomizer.collection.LinkedListRandomizer;
import org.junit.jupiter.api.BeforeEach;

import java.util.LinkedList;

import static org.assertj.core.api.Assertions.assertThat;

class LinkedListFieldTest extends ModelFieldTest {

    private PlainField<Integer> integerField;

    @BeforeEach
    @Override
    protected void setUp() {
        fieldCustomization = FieldPatternFactory.one(FIELD_NAME).minSize(2).maxSize(5).build();
        typeName = ParameterizedTypeName.get(LinkedList.class, Integer.class);
        integerField = new IntegerField().create(fieldCustomization);
        modelField = new LinkedListField().create(fieldCustomization, integerField);
    }

    @Override
    protected void should_generate_initializer() {
        final CodeBlock codeBlock = modelField.initializer();

        assertThat(codeBlock)
                .hasToString("new " + $(LinkedListRandomizer.class) + "<>(" + integerField.initializer() + ", 2, 5)");
    }
}
