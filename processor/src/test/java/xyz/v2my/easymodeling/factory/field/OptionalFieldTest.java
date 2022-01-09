package xyz.v2my.easymodeling.factory.field;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.string.StringField;
import xyz.v2my.easymodeling.factory.helper.FieldWrapperFactory;
import xyz.v2my.easymodeling.randomizer.OptionalRandomizer;

import javax.lang.model.element.Modifier;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class OptionalFieldTest extends FieldTest {

    @BeforeEach
    void setUp() {
        fieldWrapper = FieldWrapperFactory.one(FIELD_NAME).min(3.).max(9.).build();
        final List<ModelField> nestedFields = Collections.singletonList(new StringField(fieldWrapper));
        typeName = ParameterizedTypeName.get(Optional.class, Integer.class);
        modelField = new OptionalField(typeName, fieldWrapper, nestedFields);
    }

    @Override
    @Test
    protected void should_generate_builder_setter() {
        final MethodSpec setter = modelField.setter();

        assertThat(setter.name).isEqualTo(FIELD_NAME);
        assertThat(setter.returnType.toString()).isEqualTo("Builder");
        assertThat(setter.modifiers).containsExactly(Modifier.PUBLIC);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void should_generate_init_value_based_on_field_wrapper(boolean allowEmpty) {
        FieldWrapper fieldWrapper = FieldWrapperFactory.one("field_name").allowEmpty(allowEmpty).build();
        final PlainField<String> stringField = new StringField(fieldWrapper);
        final List<ModelField> nestedFields = Collections.singletonList(stringField);
        final OptionalField optionalField = new OptionalField(typeName, fieldWrapper, nestedFields);

        final CodeBlock initialValue = optionalField.initialValue();

        assertThat(initialValue.toString()).isEqualTo("new " + $(OptionalRandomizer.class) + "<>(" + stringField.initializer() + ", " + allowEmpty + ").next()");
    }
}
