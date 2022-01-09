package xyz.v2my.easymodeling.factory.field.primitive;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xyz.v2my.easymodeling.factory.field.FieldTest;
import xyz.v2my.easymodeling.factory.helper.FieldWrapperFactory;
import xyz.v2my.easymodeling.randomizer.primitive.BooleanRandomizer;

import javax.lang.model.element.Modifier;

import static org.assertj.core.api.Assertions.assertThat;

class BooleanFieldTest extends FieldTest {

    @BeforeEach
    void setUp() {
        fieldWrapper = FieldWrapperFactory.one(FIELD_NAME).build();
        typeName = TypeName.BOOLEAN;
        modelField = new BooleanField(typeName, fieldWrapper);
    }

    @Test
    @Override
    protected void should_generate_builder_setter() {
        final MethodSpec builder = modelField.setter("Builder");

        assertThat(builder.name).isEqualTo(FIELD_NAME);
        assertThat(builder.returnType.toString()).isEqualTo("Builder");
        assertThat(builder.modifiers).containsExactly(Modifier.PUBLIC);
        assertThat(builder.parameters).hasSize(1);
        final ParameterSpec parameter = builder.parameters.get(0);
        assertThat(parameter.name).isEqualTo(FIELD_NAME);
        assertThat(parameter.type.toString()).isEqualTo(TypeName.BOOLEAN.toString());
        assertThat(builder.code.toString()).isEqualTo("this." + FIELD_NAME + " = " + FIELD_NAME + ";\nreturn this;\n");
    }

    @Test
    void should_generate_initial_value() {
        final CodeBlock initialValue = modelField.initialValue();

        assertThat(initialValue.toString()).isEqualTo("new " + $(BooleanRandomizer.class) + "().next()");
    }
}
