package io.github.easymodeling.modeler.field.stream;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import io.github.easymodeling.modeler.field.FieldTest;
import io.github.easymodeling.modeler.field.number.DoubleField;
import io.github.easymodeling.modeler.helper.FieldPatternFactory;
import io.github.easymodeling.randomizer.stream.DoubleStreamRandomizer;
import org.junit.jupiter.api.BeforeEach;

import java.util.stream.DoubleStream;

import static org.assertj.core.api.Assertions.assertThat;

class DoubleStreamFieldTest extends FieldTest {

    @Override
    @BeforeEach
    protected void setUp() {
        fieldCustomization = FieldPatternFactory.one(FIELD_NAME).min(2.).max(9.).minSize(10).maxSize(15).build();
        typeName = ClassName.get(DoubleStream.class);
        modelField = new DoubleStreamField().create(fieldCustomization);
    }

    @Override
    protected void should_generate_initializer() {
        final CodeBlock initializer = modelField.initializer();

        assertThat(initializer).hasToString(
                "new " + $(DoubleStreamRandomizer.class) + "(" + new DoubleField().create(fieldCustomization).initializer() + ", 10, 15)");
    }
}
