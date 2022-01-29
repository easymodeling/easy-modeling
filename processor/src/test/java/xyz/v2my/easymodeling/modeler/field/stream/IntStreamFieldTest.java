package xyz.v2my.easymodeling.modeler.field.stream;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import org.junit.jupiter.api.BeforeEach;
import xyz.v2my.easymodeling.modeler.field.FieldTest;
import xyz.v2my.easymodeling.modeler.field.number.IntegerField;
import xyz.v2my.easymodeling.modeler.helper.FieldPatternFactory;
import xyz.v2my.easymodeling.randomizer.stream.IntStreamRandomizer;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class IntStreamFieldTest extends FieldTest {

    @Override
    @BeforeEach
    protected void setUp() {
        fieldPattern = FieldPatternFactory.one(FIELD_NAME).min(2.).max(9.).minSize(10).maxSize(15).build();
        typeName = ClassName.get(IntStream.class);
        modelField = new IntStreamField().create(fieldPattern);
    }

    @Override
    protected void should_generate_initializer() {
        final CodeBlock initializer = modelField.initializer();

        assertThat(initializer).hasToString(
                "new " + $(IntStreamRandomizer.class) + "(" + new IntegerField().create(fieldPattern).initializer() + ", 10, 15)");
    }
}
