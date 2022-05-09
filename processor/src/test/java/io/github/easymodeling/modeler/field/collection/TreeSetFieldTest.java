package io.github.easymodeling.modeler.field.collection;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.ParameterizedTypeName;
import io.github.easymodeling.modeler.field.ModelFieldTest;
import io.github.easymodeling.modeler.field.number.IntegerField;
import io.github.easymodeling.modeler.helper.FieldPatternFactory;
import io.github.easymodeling.randomizer.collection.TreeSetRandomizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.TreeSet;

import static org.assertj.core.api.Assertions.assertThat;

class TreeSetFieldTest extends ModelFieldTest {

    private IntegerField integerField;

    @Override
    @BeforeEach
    protected void setUp() {
        fieldCustomization = FieldPatternFactory.one(FIELD_NAME).maxSize(100).build();
        integerField = new IntegerField().create(fieldCustomization);
        typeName = ParameterizedTypeName.get(TreeSet.class, Integer.class);
        modelField = new TreeSetField().create(fieldCustomization, integerField);
    }

    @Override
    protected void should_generate_initializer() {
        final CodeBlock initializer = modelField.initializer();

        assertThat(initializer).hasToString(
                "new " + $(TreeSetRandomizer.class) + "<>(" + integerField.initializer() + ", 100)");
    }

    @Test
    void should_print_formatted_message() {
        assertThat(modelField).hasToString(TreeSet.class.getCanonicalName() + "<" + Integer.class.getCanonicalName() + ">");
    }
}
