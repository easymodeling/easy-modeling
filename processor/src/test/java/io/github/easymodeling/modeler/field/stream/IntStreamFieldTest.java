package io.github.easymodeling.modeler.field.stream;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import io.github.easymodeling.modeler.field.FieldTest;
import io.github.easymodeling.modeler.field.number.IntegerField;
import io.github.easymodeling.modeler.helper.FieldPatternFactory;
import io.github.easymodeling.randomizer.stream.IntStreamRandomizer;
import org.junit.jupiter.api.BeforeEach;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class IntStreamFieldTest extends FieldTest {

    @Override
    @BeforeEach
    protected void setUp() {
        fieldCustomization = FieldPatternFactory.one(FIELD_NAME).min(2.).max(9.).minSize(10).maxSize(15).build();
        typeName = ClassName.get(IntStream.class);
        modelField = new IntStreamField().create(fieldCustomization);
    }

    @Override
    protected void should_generate_initializer() {
        final CodeBlock initializer = modelField.initializer();

        assertThat(initializer).hasToString(
                "new " + $(IntStreamRandomizer.class) + "(" + new IntegerField().create(fieldCustomization).initializer() + ", 10, 15)");
    }
}
