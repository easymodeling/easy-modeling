package io.github.easymodeling.modeler.field.stream;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import org.junit.jupiter.api.BeforeEach;
import io.github.easymodeling.modeler.field.FieldTest;
import io.github.easymodeling.modeler.field.number.LongField;
import io.github.easymodeling.modeler.helper.FieldPatternFactory;
import io.github.easymodeling.randomizer.stream.LongStreamRandomizer;

import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;

class LongStreamFieldTest extends FieldTest {

    @Override
    @BeforeEach
    protected void setUp() {
        fieldPattern = FieldPatternFactory.one(FIELD_NAME).min(2.).max(9.).minSize(10).maxSize(15).build();
        typeName = ClassName.get(LongStream.class);
        modelField = new LongStreamField().create(fieldPattern);
    }

    @Override
    protected void should_generate_initializer() {
        final CodeBlock initializer = modelField.initializer();

        assertThat(initializer).hasToString(
                "new " + $(LongStreamRandomizer.class) + "(" + new LongField().create(fieldPattern).initializer() + ", 10, 15)");
    }
}
