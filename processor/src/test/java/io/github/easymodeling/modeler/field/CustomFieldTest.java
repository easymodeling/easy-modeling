package io.github.easymodeling.modeler.field;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import io.github.easymodeling.modeler.helper.FieldPatternFactory;
import io.github.easymodeling.randomizer.CustomTypeRandomizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class CustomFieldTest extends ModelFieldTest {

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

    @Test
    void should_not_create_enum_fields() {
        final Throwable throwable = catchThrowable(() -> modelField.create(fieldCustomization));

        assertThat(throwable).isInstanceOf(UnsupportedOperationException.class);
    }

    @SuppressWarnings("InnerClassMayBeStatic")
    public class SomeTest {
    }
}
