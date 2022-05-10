package io.github.easymodeling.modeler.field;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.ParameterizedTypeName;
import io.github.easymodeling.modeler.FieldCustomization;
import io.github.easymodeling.modeler.field.string.StringField;
import io.github.easymodeling.modeler.helper.FieldPatternFactory;
import io.github.easymodeling.randomizer.OptionalRandomizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class OptionalFieldTest extends ModelFieldTest {

    private StringField stringField;

    @BeforeEach
    @Override
    protected void setUp() {
        fieldCustomization = FieldPatternFactory.one(FIELD_NAME).min(3.).max(9.).build();
        stringField = new StringField().create(fieldCustomization);
        typeName = ParameterizedTypeName.get(Optional.class, String.class);
        modelField = new OptionalField().create(fieldCustomization, stringField);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void should_generate_init_value_based_on_field_wrapper(boolean allowEmpty) {
        FieldCustomization fieldCustomization = FieldPatternFactory.one("field_name").allowEmpty(allowEmpty).build();
        final PlainField<String> stringField = new StringField().create(fieldCustomization);
        final OptionalField optionalField = new OptionalField().create(fieldCustomization, stringField);

        final CodeBlock initialValue = optionalField.initialValue();

        assertThat(initialValue)
                .hasToString("new " + $(OptionalRandomizer.class) + "<>(" + stringField.initializer() + ", " + allowEmpty + ").next()");
    }

    @Override
    protected void should_generate_initializer() {
        final CodeBlock initializer = modelField.initializer();

        assertThat(initializer)
                .hasToString("new " + $(OptionalRandomizer.class) + "<>(" + stringField.initializer() + ", false)");
    }
}
