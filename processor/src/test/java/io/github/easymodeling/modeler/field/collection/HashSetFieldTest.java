package io.github.easymodeling.modeler.field.collection;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.ParameterizedTypeName;
import org.junit.jupiter.api.BeforeEach;
import io.github.easymodeling.modeler.field.FieldTest;
import io.github.easymodeling.modeler.field.number.IntegerField;
import io.github.easymodeling.modeler.helper.FieldPatternFactory;
import io.github.easymodeling.randomizer.collection.HashSetRandomizer;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

class HashSetFieldTest extends FieldTest {

    private IntegerField integerField;

    @BeforeEach
    @Override
    protected void setUp() {
        fieldPattern = FieldPatternFactory.one(FIELD_NAME).minSize(50).maxSize(100).build();
        integerField = new IntegerField().create(fieldPattern);
        typeName = ParameterizedTypeName.get(HashSet.class, Integer.class);
        modelField = new HashSetField().create(fieldPattern, integerField);
    }

    @Override
    protected void should_generate_initializer() {
        final CodeBlock initializer = modelField.initializer();

        assertThat(initializer).hasToString(
                "new " + $(HashSetRandomizer.class) + "<>(" + integerField.initializer() + ", 50, 100)");
    }
}