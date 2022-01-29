package xyz.v2my.easymodeling.modeler.field;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.ParameterizedTypeName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import xyz.v2my.easymodeling.modeler.FieldPattern;
import xyz.v2my.easymodeling.modeler.field.string.StringField;
import xyz.v2my.easymodeling.modeler.helper.FieldPatternFactory;
import xyz.v2my.easymodeling.randomizer.OptionalRandomizer;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class OptionalFieldTest extends FieldTest {

    private StringField stringField;

    @BeforeEach
    @Override
    protected void setUp() {
        fieldPattern = FieldPatternFactory.one(FIELD_NAME).min(3.).max(9.).build();
        stringField = new StringField().create(fieldPattern);
        typeName = ParameterizedTypeName.get(Optional.class, String.class);
        modelField = new OptionalField().create(fieldPattern, stringField);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void should_generate_init_value_based_on_field_wrapper(boolean allowEmpty) {
        FieldPattern fieldPattern = FieldPatternFactory.one("field_name").allowEmpty(allowEmpty).build();
        final PlainField<String> stringField = new StringField().create(fieldPattern);
        final OptionalField optionalField = new OptionalField().create(fieldPattern, stringField);

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
