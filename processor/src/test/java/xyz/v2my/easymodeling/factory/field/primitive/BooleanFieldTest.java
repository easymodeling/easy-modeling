package xyz.v2my.easymodeling.factory.field.primitive;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xyz.v2my.easymodeling.factory.field.FieldTest;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.PlainField;
import xyz.v2my.easymodeling.factory.helper.FieldWrapperFactory;
import xyz.v2my.easymodeling.randomizer.primitive.BooleanRandomizer;

import javax.lang.model.element.Modifier;

import static org.assertj.core.api.Assertions.assertThat;

class BooleanFieldTest extends FieldTest {

    private PlainField<Boolean> booleanPlainField;

    @BeforeEach
    void setUp() {
        final FieldWrapper fieldWrapper = FieldWrapperFactory.one("booleanField").build();
        booleanPlainField = new BooleanField().create(TypeName.BOOLEAN, fieldWrapper);
    }

    @Test
    @Override
    protected void should_generate_builder_field() {
        final FieldSpec field = booleanPlainField.field();

        assertThat(field.name).contains("booleanField");
        assertThat(field.modifiers).containsExactly(Modifier.PRIVATE);
        assertThat(field.type).isEqualTo(TypeName.BOOLEAN);
    }

    @Test
    @Override
    protected void should_generate_builder_setter() {
        final MethodSpec builder = booleanPlainField.setter("Builder");

        assertThat(builder.name).contains("booleanField");
        assertThat(builder.returnType.toString()).isEqualTo("Builder");
        assertThat(builder.modifiers).containsExactly(Modifier.PUBLIC);
        assertThat(builder.parameters).hasSize(1);
        final ParameterSpec parameter = builder.parameters.get(0);
        assertThat(parameter.name).contains("booleanField");
        assertThat(parameter.type.toString()).isEqualTo(TypeName.BOOLEAN.toString());
        assertThat(builder.code.toString()).contains("this.booleanField").contains(" = ").contains("booleanField").contains(";");
    }

    @Test
    void should_generate_initial_value() {
        final CodeBlock initialValue = booleanPlainField.initialValue();

        assertThat(initialValue.toString()).isEqualTo("new " + $(BooleanRandomizer.class) + "().next()");
    }
}
