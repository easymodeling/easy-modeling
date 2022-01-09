package xyz.v2my.easymodeling.factory.field;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.TypeName;
import org.junit.jupiter.api.Test;
import xyz.v2my.easymodeling.factory.FieldWrapper;

import javax.lang.model.element.Modifier;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class FieldTest {

    public static final String FIELD_NAME = "fieldName";

    protected FieldWrapper fieldWrapper;

    protected TypeName typeName;

    protected ModelField modelField;

    protected String $(Class<?> clazz) {
        return clazz.getCanonicalName();
    }

    @Test
    final void should_generate_builder_field() {
        final FieldSpec field = modelField.field();

        assertThat(field.modifiers).containsExactly(Modifier.PRIVATE);
        assertThat(field.type).isEqualTo(typeName);
        assertThat(field.name).isEqualTo(FIELD_NAME);
    }

    @Test
    abstract protected void should_generate_builder_setter();
}
