package io.github.easymodeling.modeler.field.stream;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.ParameterizedTypeName;
import io.github.easymodeling.modeler.field.ModelFieldTest;
import io.github.easymodeling.modeler.field.PlainField;
import io.github.easymodeling.modeler.field.number.IntegerField;
import io.github.easymodeling.modeler.helper.FieldPatternFactory;
import io.github.easymodeling.randomizer.stream.StreamRandomizer;
import org.junit.jupiter.api.BeforeEach;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class StreamFieldTest extends ModelFieldTest {

    private PlainField<Integer> integerField;

    @BeforeEach
    @Override
    protected void setUp() {
        fieldCustomization = FieldPatternFactory.one(FIELD_NAME).minSize(2).maxSize(9).build();
        integerField = new IntegerField().create(fieldCustomization);
        typeName = ParameterizedTypeName.get(Stream.class, Integer.class);
        modelField = new StreamField().create(fieldCustomization, integerField);
    }

    @Override
    protected void should_generate_initializer() {
        final CodeBlock initializer = modelField.initializer();

        assertThat(initializer)
                .hasToString("new " + $(StreamRandomizer.class) + "<>(" + integerField.initializer() + ", 2, 9)");
    }
}
