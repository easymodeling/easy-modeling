package xyz.v2my.easymodeling.factory.field.primitive;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xyz.v2my.easymodeling.factory.field.FieldTest;
import xyz.v2my.easymodeling.factory.helper.FieldWrapperFactory;
import xyz.v2my.easymodeling.randomizer.primitive.CharRandomizer;

import static org.assertj.core.api.Assertions.assertThat;

class CharFieldTest extends FieldTest {

    @BeforeEach
    @Override
    protected void setUp() {
        fieldWrapper = FieldWrapperFactory.one(FIELD_NAME).build();
        typeName = ClassName.get(Character.class);
        modelField = new CharField(fieldWrapper);
    }

    @Test
    @Override
    protected void should_generate_initializer() {
        final CodeBlock initialValue = modelField.initializer();

        assertThat(initialValue.toString()).isEqualTo("new " + $(CharRandomizer.class) + "()");
    }
}
