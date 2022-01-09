package xyz.v2my.easymodeling.factory.field.primitive;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import org.junit.jupiter.api.Test;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.FieldTest;
import xyz.v2my.easymodeling.factory.helper.FieldWrapperFactory;
import xyz.v2my.easymodeling.randomizer.primitive.CharRandomizer;

import javax.lang.model.element.Modifier;

import static org.assertj.core.api.Assertions.assertThat;

class CharFieldTest extends FieldTest {

    @Override
    @Test
    protected void should_generate_builder_field() {
        final FieldWrapper fieldWrapper = FieldWrapperFactory.one("aChar").build();
        final CharField charField = new CharField(TypeName.CHAR, fieldWrapper);

        final FieldSpec field = charField.field();

        assertThat(field.name).contains("aChar");
        assertThat(field.type).isEqualTo(TypeName.CHAR);
        assertThat(field.modifiers).containsExactly(Modifier.PRIVATE);
    }

    @Override
    @Test
    protected void should_generate_builder_setter() {
        final FieldWrapper fieldWrapper = FieldWrapperFactory.one("aChar").build();
        final CharField charField = new CharField(TypeName.CHAR, fieldWrapper);

        final MethodSpec setter = charField.setter("Builder");

        assertThat(setter.name).isEqualTo("aChar");
        assertThat(setter.returnType.toString()).isEqualTo("Builder");
        assertThat(setter.modifiers).containsExactly(Modifier.PUBLIC);
        assertThat(setter.parameters).hasSize(1);
        final ParameterSpec parameter = setter.parameters.get(0);
        assertThat(parameter.name).contains("aChar");
        assertThat(parameter.type).isEqualTo(TypeName.CHAR);
    }

    @Test
    void should_generate_initial_value() {
        final FieldWrapper fieldWrapper = FieldWrapperFactory.one("aChar").build();
        final CharField charField = new CharField(TypeName.CHAR, fieldWrapper);

        final CodeBlock initialValue = charField.initialValue();

        assertThat(initialValue.toString()).isEqualTo("new " + $(CharRandomizer.class) + "().next()");
    }
}
