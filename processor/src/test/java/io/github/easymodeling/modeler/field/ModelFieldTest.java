package io.github.easymodeling.modeler.field;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import io.github.easymodeling.ReflectionUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.lang.model.element.Modifier;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class ModelFieldTest extends AbstractFieldTest {

    @BeforeEach
    protected abstract void setUp();

    @Test
    final void should_generate_builder_field() {
        final FieldSpec field = modelField.field();

        assertThat(field.modifiers).containsExactly(Modifier.PRIVATE);
        assertThat(field.type).isEqualTo(typeName);
        assertThat(field.name).isEqualTo(FIELD_NAME);
    }

    @Test
    final void should_generate_builder_setter() {
        final MethodSpec setter = modelField.setter();

        assertThat(setter.name).isEqualTo(FIELD_NAME);
        assertThat(setter.returnType).hasToString("Builder");
        assertThat(setter.parameters).hasSize(1);
        assertThat(setter.parameters.get(0).name).isEqualTo(FIELD_NAME);
        assertThat(setter.parameters.get(0).type).isEqualTo(typeName);
        final String code = "" +
                "this." + FIELD_NAME + " = " + FIELD_NAME + ";\n" +
                "return this;\n";
        assertThat(setter.code).hasToString(code);
    }

    @Test
    void should_generate_constructor_statement() {
        final CodeBlock codeBlock = modelField.constructorStatement();

        final String code = "" + "this." + FIELD_NAME + " = (" + typeName.box() + ") " + $(ReflectionUtil.class) + ".getField(model, \"" + QUALIFIED_NAME + "\")";
        assertThat(codeBlock).hasToString(code);
    }

    @Test
    void should_generate_populate_statement() {
        final CodeBlock codeBlock = modelField.populateStatement();

        final String code = $(ReflectionUtil.class) + ".setField(model, \"" + QUALIFIED_NAME + "\", " + modelField.initialValue() + ")";
        assertThat(codeBlock).hasToString(code);
    }

    @Test
    void should_generate_build_statement() {
        final CodeBlock codeBlock = modelField.buildStatement();

        final String code = $(ReflectionUtil.class) + ".setField(model, \"" + QUALIFIED_NAME + "\", " + FIELD_NAME + ")";
        assertThat(codeBlock).hasToString(code);
    }

    @Test
    void should_generate_initial_value() {
        final CodeBlock initializer = modelField.initializer();
        final CodeBlock initialValue = modelField.initialValue();

        assertThat(initialValue).isEqualTo(CodeBlock.of("$L.next()", initializer));
    }

    @Test
    void should_get_field_name() {
        final String fieldName = modelField.fieldName();

        assertThat(fieldName).isEqualTo(FIELD_NAME);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void should_set_inherited(boolean inherited) {
        modelField.setInherited(inherited);

        assertThat(modelField.isInherited()).isEqualTo(inherited);
    }

    @Test
    void should_set_hidden() {
        assertThat(modelField.isHidden()).isFalse();

        modelField.setHidden();

        assertThat(modelField.isHidden()).isTrue();
    }

    @Test
    void should_generate_initializer_() {
        should_generate_initializer();
    }

    protected abstract void should_generate_initializer();
}
