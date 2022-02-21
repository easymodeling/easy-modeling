package io.github.easymodeling.modeler.field.collection;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.ParameterizedTypeName;
import io.github.easymodeling.modeler.field.FieldTest;
import io.github.easymodeling.modeler.field.string.StringField;
import io.github.easymodeling.modeler.helper.FieldPatternFactory;
import io.github.easymodeling.randomizer.collection.ListRandomizer;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ListFieldTest extends FieldTest {

    private StringField stringField;

    @BeforeEach
    @Override
    protected void setUp() {
        fieldPattern = FieldPatternFactory.one(FIELD_NAME).string("").min(3.).max(9.).minSize(20).maxSize(50).build();
        this.stringField = new StringField().create(fieldPattern);
        typeName = ParameterizedTypeName.get(List.class, String.class);
        modelField = new ListField().create(fieldPattern, this.stringField);
    }

    @Override
    protected void should_generate_initializer() {
        final CodeBlock initializer = modelField.initializer();

        assertThat(initializer)
                .hasToString("new " + $(ListRandomizer.class) + "<>(" + stringField.initializer() + ", 20, 50)");
    }
}
