package xyz.v2my.easymodeling.factory.field;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
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

    @Override
    @Test
    protected void should_generate_builder_field() {
        FieldWrapper fieldWrapper = FieldWrapperFactory.one("field_name").min(3.).max(9.).build();
        final List<ModelField> nestedFields = Collections.singletonList(new StringField(ClassName.get(String.class), fieldWrapper));
        final OptionalField optionalField = new OptionalField(ParameterizedTypeName.get(Optional.class, Integer.class), fieldWrapper, nestedFields);

        final FieldSpec field = optionalField.field();

        assertThat(field.name).contains("field_name");
        assertThat(field.type).isEqualTo(ParameterizedTypeName.get(Optional.class, Integer.class));
        assertThat(field.modifiers).containsExactly(Modifier.PRIVATE);
    }

    @Override
    @Test
    protected void should_generate_builder_setter() {
        FieldWrapper fieldWrapper = FieldWrapperFactory.one("field_name").build();
        final List<ModelField> nestedFields = Collections.singletonList(new StringField(ClassName.get(String.class), fieldWrapper));
        final OptionalField optionalField = new OptionalField(ParameterizedTypeName.get(Optional.class, Integer.class), fieldWrapper, nestedFields);

        final MethodSpec setter = optionalField.setter("Builder");

        assertThat(setter.name).isEqualTo("field_name");
        assertThat(setter.returnType.toString()).isEqualTo("Builder");
        assertThat(setter.modifiers).containsExactly(Modifier.PUBLIC);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void should_generate_(boolean allowEmpty) {
        FieldWrapper fieldWrapper = FieldWrapperFactory.one("field_name").allowEmpty(allowEmpty).build();
        final PlainField<String> stringField = new StringField(ClassName.get(String.class), fieldWrapper);
        final List<ModelField> nestedFields = Collections.singletonList(stringField);
        final OptionalField optionalField = new OptionalField(ParameterizedTypeName.get(Optional.class, Integer.class), fieldWrapper, nestedFields);

        final CodeBlock initialValue = optionalField.initialValue();

        assertThat(initialValue.toString()).isEqualTo("new " + $(OptionalRandomizer.class) + "<>(" + stringField.initializer() + ", " + allowEmpty + ").next()");
    }
}
