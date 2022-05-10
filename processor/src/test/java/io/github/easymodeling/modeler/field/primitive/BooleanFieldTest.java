package io.github.easymodeling.modeler.field.primitive;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import io.github.easymodeling.modeler.field.ModelFieldTest;
import io.github.easymodeling.modeler.helper.FieldPatternFactory;
import io.github.easymodeling.randomizer.primitive.BooleanRandomizer;
import org.junit.jupiter.api.BeforeEach;

import static org.assertj.core.api.Assertions.assertThat;

class BooleanFieldTest extends ModelFieldTest {

    @BeforeEach
    @Override
    protected void setUp() {
        fieldCustomization = FieldPatternFactory.one(FIELD_NAME).build();
        typeName = ClassName.get(Boolean.class);
        modelField = new BooleanField().create(fieldCustomization);
    }

    @Override
    protected void should_generate_initializer() {
        final CodeBlock initialValue = modelField.initializer();

        assertThat(initialValue).hasToString("new " + $(BooleanRandomizer.class) + "()");
    }
}
