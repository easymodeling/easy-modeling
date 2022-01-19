package xyz.v2my.easymodeling.factory.field.primitive;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import org.junit.jupiter.api.BeforeEach;
import xyz.v2my.easymodeling.factory.field.FieldTest;
import xyz.v2my.easymodeling.factory.helper.FieldWrapperFactory;
import xyz.v2my.easymodeling.randomizer.primitive.BooleanRandomizer;

import static org.assertj.core.api.Assertions.assertThat;

class BooleanFieldTest extends FieldTest {

    @BeforeEach
    @Override
    protected void setUp() {
        fieldWrapper = FieldWrapperFactory.one(FIELD_NAME).build();
        typeName = ClassName.get(Boolean.class);
        modelField = new BooleanField().create(fieldWrapper);
    }

    @Override
    protected void should_generate_initializer() {
        final CodeBlock initialValue = modelField.initializer();

        assertThat(initialValue.toString()).isEqualTo("new " + $(BooleanRandomizer.class) + "()");
    }
}
