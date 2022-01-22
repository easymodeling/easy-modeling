package xyz.v2my.easymodeling.factory.field.stream;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import org.junit.jupiter.api.BeforeEach;
import xyz.v2my.easymodeling.factory.field.FieldTest;
import xyz.v2my.easymodeling.factory.field.number.DoubleField;
import xyz.v2my.easymodeling.factory.helper.FieldWrapperFactory;
import xyz.v2my.easymodeling.randomizer.stream.DoubleStreamRandomizer;

import java.util.stream.DoubleStream;

import static org.assertj.core.api.Assertions.assertThat;

class DoubleStreamFieldTest extends FieldTest {

    @Override
    @BeforeEach
    protected void setUp() {
        fieldWrapper = FieldWrapperFactory.one(FIELD_NAME).min(2.).max(9.).minSize(10).maxSize(15).build();
        typeName = ClassName.get(DoubleStream.class);
        modelField = new DoubleStreamField().create(fieldWrapper);
    }

    @Override
    protected void should_generate_initializer() {
        final CodeBlock initializer = modelField.initializer();

        assertThat(initializer.toString()).isEqualTo(
                "new " + $(DoubleStreamRandomizer.class) + "(" + new DoubleField().create(fieldWrapper).initializer() + ", 10, 15)");
    }
}
