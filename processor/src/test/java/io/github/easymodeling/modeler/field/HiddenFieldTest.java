package io.github.easymodeling.modeler.field;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import io.github.easymodeling.ReflectionUtil;
import io.github.easymodeling.modeler.field.number.ShortField;
import io.github.easymodeling.modeler.helper.FieldPatternFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.lang.model.element.Modifier;

import static org.assertj.core.api.Assertions.assertThat;

public class HiddenFieldTest extends AbstractFieldTest {

    public static final String QUALIFIED_FIELD_NAME_AS_VARIABLE = QUALIFIED_NAME.replace(".", "_").replace("#", "$");

    @BeforeEach
    protected void setUp() {
        fieldCustomization = FieldPatternFactory.one(FIELD_NAME).min(-2.).max(9.).build();
        typeName = ClassName.get(Short.class);
        modelField = new ShortField().create(fieldCustomization);
        modelField.setHidden();
    }

    @Test
    final void should_generate_builder_field_with_qualified_field_name() {
        final FieldSpec field = modelField.field();

        assertThat(field.modifiers).containsExactly(Modifier.PRIVATE);
        assertThat(field.type).isEqualTo(typeName);
        assertThat(field.name).isEqualTo(QUALIFIED_FIELD_NAME_AS_VARIABLE);
    }

    @Test
    void should_generate_constructor_statement_with_qualified_field_name() {
        final CodeBlock codeBlock = modelField.constructorStatement();

        final String code = "" + "this." + QUALIFIED_FIELD_NAME_AS_VARIABLE + " = (" + typeName.box() + ") " + $(ReflectionUtil.class) + ".getField(model, \"" + QUALIFIED_NAME + "\")";
        assertThat(codeBlock).hasToString(code);
    }

    @Test
    void should_generate_build_statement() {
        final CodeBlock codeBlock = modelField.buildStatement();

        final String code = $(ReflectionUtil.class) + ".setField(model, \"" + QUALIFIED_NAME + "\", " + QUALIFIED_FIELD_NAME_AS_VARIABLE + ")";
        assertThat(codeBlock).hasToString(code);
    }
}
