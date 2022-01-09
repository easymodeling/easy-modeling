package xyz.v2my.easymodeling.factory.field.primitive;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.FieldTest;
import xyz.v2my.easymodeling.factory.helper.FieldWrapperFactory;
import xyz.v2my.easymodeling.randomizer.primitive.CharRandomizer;

import javax.lang.model.element.Modifier;

import static org.assertj.core.api.Assertions.assertThat;

class CharFieldTest extends FieldTest {

    @BeforeEach
    void setUp() {
        fieldWrapper = FieldWrapperFactory.one(FIELD_NAME).build();
        typeName = ClassName.get(Character.class);
        modelField = new CharField(fieldWrapper);
    }

    @Override
    @Test
    protected void should_generate_builder_setter() {
        final FieldWrapper fieldWrapper = FieldWrapperFactory.one(FIELD_NAME).build();
        final CharField charField = new CharField(fieldWrapper);

        final MethodSpec setter = charField.setter();

        assertThat(setter.name).isEqualTo(FIELD_NAME);
        assertThat(setter.returnType.toString()).isEqualTo("Builder");
        assertThat(setter.modifiers).containsExactly(Modifier.PUBLIC);
        assertThat(setter.parameters).hasSize(1);
        final ParameterSpec parameter = setter.parameters.get(0);
        assertThat(parameter.name).contains(FIELD_NAME);
        assertThat(parameter.type).isEqualTo(ClassName.get(Character.class));
    }

    @Test
    void should_generate_initial_value() {
        final FieldWrapper fieldWrapper = FieldWrapperFactory.one(FIELD_NAME).build();
        final CharField charField = new CharField(fieldWrapper);

        final CodeBlock initialValue = charField.initialValue();

        assertThat(initialValue.toString()).isEqualTo("new " + $(CharRandomizer.class) + "().next()");
    }
}
