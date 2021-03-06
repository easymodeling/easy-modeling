package io.github.easymodeling.modeler.field.collection;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.ParameterizedTypeName;
import io.github.easymodeling.modeler.field.ModelFieldTest;
import io.github.easymodeling.modeler.field.number.IntegerField;
import io.github.easymodeling.modeler.helper.FieldPatternFactory;
import io.github.easymodeling.randomizer.collection.HashSetRandomizer;
import org.junit.jupiter.api.BeforeEach;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

class HashSetFieldTest extends ModelFieldTest {

    private IntegerField integerField;

    @BeforeEach
    @Override
    protected void setUp() {
        fieldCustomization = FieldPatternFactory.one(FIELD_NAME).maxSize(100).build();
        integerField = new IntegerField().create(fieldCustomization);
        typeName = ParameterizedTypeName.get(HashSet.class, Integer.class);
        modelField = new HashSetField().create(fieldCustomization, integerField);
    }

    @Override
    protected void should_generate_initializer() {
        final CodeBlock initializer = modelField.initializer();

        assertThat(initializer).hasToString(
                "new " + $(HashSetRandomizer.class) + "<>(" + integerField.initializer() + ", 100)");
    }
}
