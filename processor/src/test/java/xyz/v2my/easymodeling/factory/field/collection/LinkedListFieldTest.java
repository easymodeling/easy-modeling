package xyz.v2my.easymodeling.factory.field.collection;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xyz.v2my.easymodeling.factory.field.FieldTest;
import xyz.v2my.easymodeling.factory.field.PlainField;
import xyz.v2my.easymodeling.factory.field.number.IntegerField;
import xyz.v2my.easymodeling.factory.helper.FieldWrapperFactory;
import xyz.v2my.easymodeling.randomizer.collection.LinkedListRandomizer;

import java.util.Collections;
import java.util.LinkedList;

import static org.assertj.core.api.Assertions.assertThat;

class LinkedListFieldTest extends FieldTest {

    private PlainField<Integer> integerField;

    @BeforeEach
    void setUp() {
        fieldWrapper = FieldWrapperFactory.one(FIELD_NAME).minSize(2).maxSize(5).build();
        typeName = ParameterizedTypeName.get(LinkedList.class, Integer.class);
        integerField = new IntegerField(fieldWrapper);
        modelField = new LinkedListField(ClassName.get(Integer.class), fieldWrapper, Collections.singletonList(integerField));
    }

    @Override
    @Test
    protected void should_generate_builder_setter() {
        final MethodSpec setter = modelField.setter();

        assertThat(setter.name).isEqualTo(FIELD_NAME);
        assertThat(setter.returnType.toString()).isEqualTo("Builder");
        assertThat(setter.parameters).hasSize(1);
        assertThat(setter.parameters.get(0).name).isEqualTo(FIELD_NAME);
        assertThat(setter.parameters.get(0).type).isEqualTo(typeName);
        assertThat(setter.code.toString()).isEqualTo("this." + FIELD_NAME + " = " + FIELD_NAME + ";\n" + "return this;\n");
    }

    @Test
    void should_generate_initial_value() {
        final CodeBlock codeBlock = modelField.initialValue();

        assertThat(codeBlock.toString())
                .isEqualTo("new " + $(LinkedListRandomizer.class) + "<>(" + integerField.initializer() + ", 2, 5).next()");
    }
}
