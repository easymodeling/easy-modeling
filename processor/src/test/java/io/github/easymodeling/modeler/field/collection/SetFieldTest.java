package io.github.easymodeling.modeler.field.collection;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.ParameterizedTypeName;
import org.junit.jupiter.api.BeforeEach;
import io.github.easymodeling.modeler.field.FieldTest;
import io.github.easymodeling.modeler.field.string.StringField;
import io.github.easymodeling.modeler.helper.FieldPatternFactory;
import io.github.easymodeling.randomizer.collection.SetRandomizer;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class SetFieldTest extends FieldTest {

    private StringField stringField;

    @BeforeEach
    @Override
    protected void setUp() {
        fieldPattern = FieldPatternFactory.one(FIELD_NAME).minSize(30).maxSize(50).build();
        typeName = ParameterizedTypeName.get(Set.class, String.class);
        stringField = new StringField().create(fieldPattern);
        modelField = new SetField().create(fieldPattern, stringField);
    }

    @Override
    protected void should_generate_initializer() {
        final CodeBlock initializer = modelField.initializer();

        assertThat(initializer)
                .hasToString("new " + $(SetRandomizer.class) + "<>(" + stringField.initializer() + ", 30, 50)");
    }
}