package xyz.v2my.easymodeling.factory.field.collection;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xyz.v2my.easymodeling.factory.FieldTest;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.ModelField;
import xyz.v2my.easymodeling.factory.field.string.StringField;
import xyz.v2my.easymodeling.factory.helper.FieldWrapperFactory;
import xyz.v2my.easymodeling.randomizer.collection.ListRandomizer;
import xyz.v2my.easymodeling.randomizer.string.StringRandomizer;

import javax.lang.model.element.Modifier;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ListFieldTest extends FieldTest {

    private TypeName typeName;
    private FieldWrapper fieldWrapper;
    private ListField listField;

    @BeforeEach
    void setUp() {
        typeName = ParameterizedTypeName.get(List.class, String.class);
        fieldWrapper = FieldWrapperFactory.one().string("").min(3.).max(9.).minLength(20).maxLength(50).build();
        final List<ModelField> nestedFields = Collections.singletonList(new StringField().create(ClassName.get(String.class), fieldWrapper));

        listField = new ListField().create(typeName, fieldWrapper, nestedFields);
    }

    @Test
    void should_generate_builder_field() {
        final FieldSpec field = listField.field();

        assertThat(field.type).isEqualTo(typeName);
        assertThat(field.name).contains(fieldWrapper.name());
        assertThat(field.modifiers).containsExactly(Modifier.PRIVATE);
    }

    @Test
    void should_generate_builder_setter() {
        final MethodSpec setter = listField.setter("Some_builder_name");

        assertThat(setter.returnType.toString()).isEqualTo("Some_builder_name");
        assertThat(setter.name).isEqualTo(fieldWrapper.name());
        assertThat(setter.modifiers).containsExactly(Modifier.PUBLIC);
        assertThat(setter.parameters).hasSize(1);
        assertThat(setter.parameters.get(0).type).isEqualTo(typeName);
        assertThat(setter.parameters.get(0).name).contains(fieldWrapper.name());
    }

    @Test
    void should_generate_initializer() {
        final CodeBlock initializer = listField.initializer();

        final String stringRandomizer = "new " + $(StringRandomizer.class) + "(3, 9, 3)";
        assertThat(initializer.toString()).isEqualTo("new " + $(ListRandomizer.class) + "<>(" + stringRandomizer + ", 20, 50)");
    }
}
