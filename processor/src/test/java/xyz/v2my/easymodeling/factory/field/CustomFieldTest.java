package xyz.v2my.easymodeling.factory.field;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import org.junit.jupiter.api.BeforeEach;
import xyz.v2my.easymodeling.factory.helper.FieldPatternFactory;

import static org.assertj.core.api.Assertions.assertThat;

class CustomFieldTest extends FieldTest {

    @Override
    @BeforeEach
    protected void setUp() {
        fieldPattern = FieldPatternFactory.one(FIELD_NAME).build();
        typeName = ClassName.get(SomeTest.class);
        modelField = new CustomField(typeName, fieldPattern);
    }

    @Override
    protected void should_generate_initializer() {
        final CodeBlock initializer = modelField.initializer();

        assertThat(initializer).hasToString("new " + $(SomeTest.class) + "Modeler()");
    }

    @SuppressWarnings("InnerClassMayBeStatic")
    public class SomeTest {
    }
}
