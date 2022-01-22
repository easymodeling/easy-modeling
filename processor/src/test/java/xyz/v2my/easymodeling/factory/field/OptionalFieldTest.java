package xyz.v2my.easymodeling.factory.field;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.ParameterizedTypeName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.string.StringField;
import xyz.v2my.easymodeling.factory.helper.FieldWrapperFactory;
import xyz.v2my.easymodeling.randomizer.OptionalRandomizer;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class OptionalFieldTest extends FieldTest {

    private StringField stringField;

    @BeforeEach
    @Override
    protected void setUp() {
        fieldWrapper = FieldWrapperFactory.one(FIELD_NAME).min(3.).max(9.).build();
        stringField = new StringField().create(fieldWrapper);
        typeName = ParameterizedTypeName.get(Optional.class, String.class);
        modelField = new OptionalField().create(fieldWrapper, stringField);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void should_generate_init_value_based_on_field_wrapper(boolean allowEmpty) {
        FieldWrapper fieldWrapper = FieldWrapperFactory.one("field_name").allowEmpty(allowEmpty).build();
        final PlainField<String> stringField = new StringField().create(fieldWrapper);
        final OptionalField optionalField = new OptionalField().create(fieldWrapper, stringField);

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
