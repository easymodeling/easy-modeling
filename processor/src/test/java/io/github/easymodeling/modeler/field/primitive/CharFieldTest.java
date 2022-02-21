package io.github.easymodeling.modeler.field.primitive;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import io.github.easymodeling.modeler.field.FieldTest;
import io.github.easymodeling.modeler.helper.FieldPatternFactory;
import io.github.easymodeling.randomizer.primitive.CharRandomizer;
import org.junit.jupiter.api.BeforeEach;

import static org.assertj.core.api.Assertions.assertThat;

class CharFieldTest extends FieldTest {

    @BeforeEach
    @Override
    protected void setUp() {
        fieldPattern = FieldPatternFactory.one(FIELD_NAME).build();
        typeName = ClassName.get(Character.class);
        modelField = new CharField().create(fieldPattern);
    }

    @Override
    protected void should_generate_initializer() {
        final CodeBlock initialValue = modelField.initializer();

        assertThat(initialValue).hasToString("new " + $(CharRandomizer.class) + "()");
    }
}
