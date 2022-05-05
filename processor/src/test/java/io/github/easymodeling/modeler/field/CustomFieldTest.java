package io.github.easymodeling.modeler.field;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import io.github.easymodeling.modeler.helper.FieldPatternFactory;
import io.github.easymodeling.randomizer.CustomTypeRandomizer;
import org.junit.jupiter.api.BeforeEach;

import static org.assertj.core.api.Assertions.assertThat;

class CustomFieldTest extends FieldTest {

    @Override
    @BeforeEach
    protected void setUp() {
        fieldCustomization = FieldPatternFactory.one(FIELD_NAME).build();
        typeName = ClassName.get(SomeTest.class);
        modelField = new CustomField(typeName, fieldCustomization);
    }

    @Override
    protected void should_generate_initializer() {
        final CodeBlock initializer = modelField.initializer();

        assertThat(initializer).hasToString("new " + $(CustomTypeRandomizer.class) + "<>(new " + $(SomeTest.class) + "Modeler(), modelCache)");
    }

    @SuppressWarnings("InnerClassMayBeStatic")
    public class SomeTest {
    }
}
